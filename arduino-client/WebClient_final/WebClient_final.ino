/*
   WEB client using Ethernet shield W5100 and DHT11 sensor
*/

//ethernet
#include <Ethernet.h>

//DHT sensor
#include <DHT.h>
#include <DHT_U.h>
#include "DHT.h"

//======Ethernet======//
//MAC address for shield
byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
//adress name for server to connect
char server[] = "greenhouse-school.herokuapp.com";
// Set the static IP address to use if the DHCP fails to assign
IPAddress ip(192, 168, 0, 177);
//The Ethernet client
EthernetClient client;
// last time you connected to the server, in milliseconds
unsigned long lastSendSensor = 0, lastGetSetPoints = 0;
// 10 sec delay between updates, in milliseconds (L used for long type)
const unsigned long postSensorInterval = 10L * 1000L, getSetPointsInterval = 10L * 1000L;

//======Sensor======//
// what digital pin we're connected to
#define DHTPIN 7
// Sensor version ;DHT 11
#define DHTTYPE DHT11
// Initialize DHT sensor.
DHT dht(DHTPIN, DHTTYPE);
int tempVal = 0, humidityVal = 0;

//=====Command paramteres=======//
// values for setpoints (humidity, temperature)
int hSP = 60, tSP = 20;

void setup() {

  pinMode(8, OUTPUT);

  Serial.println("DHT11 online!");
  dht.begin();

  // Open serial communications and wait for port to open:
  Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }

  // start the Ethernet connection:
  if (Ethernet.begin(mac) == 0) {
    Serial.println("Failed to configure Ethernet using DHCP");
    // try to congifure using IP address instead of DHCP:
    Ethernet.begin(mac, ip);
  }
  // give the Ethernet shield a second to initialize:
  delay(1000);
  Serial.println("connecting...");
}

// this method makes a HTTP POST to the server with temperature and humidity:
void httpPostSensor() {
  // close any connection before send a new request.
 
  if (client.connect(server, 80)) {
    Serial.println("connected");
    tempVal = dht.readTemperature();
    humidityVal = dht.readHumidity();
    //Read data from sensor and create json
    String postContent = "";
    postContent += "{\"temperature\" :";
    postContent += tempVal;
    postContent += ",\"humidity\" :";
    postContent += humidityVal;
    postContent += "}";
    //Serial.println(postContent);

    // Make a HTTP request:
    String postRequest = "POST /sensor HTTP/1.1\r\n";
    postRequest += "Host: greenhouse-school.herokuapp.com\r\n";
    postRequest += "Content-Type: application/json\r\n";
    postRequest += "Content-Length: " + String(postContent.length()) + "\r\n";
    postRequest += "\r\n" + postContent;
    client.print(postRequest);
   // Serial.println("Send sensor");
    lastSendSensor = millis();
  } else {
    // if you didn't get a connection to the server:
    Serial.println("connection failed");
  }
}

void httpGetSetPoints() {
  // close any connection before send a new request.
  
  if (client.connect(server, 80)) {
    Serial.println("connected");
    client.println("GET /set-points HTTP/1.1");
    client.println("Host: greenhouse-school.herokuapp.com");
    client.println("Connection: close");
    client.println();
    lastGetSetPoints = millis();

   // Serial.println("GetSetPoints");
  }
  else {
    // if you didn't get a connection to the server:
    Serial.println("connection failed");
  }
}

// Prepocessing function
void jsonSetPointsProcessing(String response) {
  //Serial.println(response);
  int iStart = response.indexOf('{');
  int iEnd = response.indexOf('}');
  String json = response.substring(iStart + 1, iEnd);

  int count = 0;
  char jsonArray[json.length()];
  json.toCharArray(jsonArray, json.length());
  for (int i = 0; i < json.length(); i++) {
    if (jsonArray[i] == ':') {
      count++;
      if (count == 2) {
        tSP = json.substring(i + 1, i + 3).toInt();
      }
      if (count == 3) {
        hSP = json.substring(i + 1, i + 3).toInt();
      }
    }
  }
}

void loop() {

  if (client.available()) {
    String response = client.readString();
    jsonSetPointsProcessing(response);
    client.stop();
  }

  if (millis() - lastSendSensor > postSensorInterval) {
    httpPostSensor();
  }
  if (millis() - lastGetSetPoints > getSetPointsInterval) {
    httpGetSetPoints();
  }

  if ( dht.readTemperature() < tSP) {
    Serial.println();
    Serial.print(dht.readTemperature());
    Serial.print("<");
    Serial.print(tSP);
    digitalWrite(8, HIGH);
  }
  else {
    Serial.println();
    Serial.print(dht.readTemperature());
    Serial.print(">");
    Serial.print(tSP);
    digitalWrite(8, LOW);
  }

  delay(1000);
}

