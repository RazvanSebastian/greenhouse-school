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
IPAddress ip(172, 27, 125, 109);
//The Ethernet client
EthernetClient clientGet;
EthernetClient clientPost;
// last time you connected to the server, in milliseconds
unsigned long lastSendSensor = 0, lastGetSetPoints = 0;
// 10 sec delay between updates, in milliseconds (L used for long type)
const unsigned long postSensorInterval = 30L * 1000L, getSetPointsInterval = 10L * 1000L;

//======Sensor======//
// what digital pin we're connected to
#define DHTPIN 7
// Sensor version ;DHT 11
#define DHTTYPE DHT11
// Initialize DHT sensor.
DHT dht(DHTPIN, DHTTYPE);

//=====Command paramteres=======//
// values for setpoints (humidity, temperature)
static int hSP = 60, tSP = 20;

void setup() {

  pinMode(8, OUTPUT); //bec
  pinMode(3, OUTPUT); // fan in
  pinMode(2, OUTPUT); // fan out
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
  clientPost.stop();
  if (clientPost.connect(server, 80)) {
    Serial.println("connected");

    //Read data from sensor and create json
    String postContent = "";
    postContent += "{\"temperature\" :";
    postContent += dht.readTemperature();
    postContent += ",\"humidity\" :";
    postContent += dht.readHumidity();
    postContent += "}";
    Serial.println(postContent);

    // Make a HTTP request:
    String postRequest = "POST /sensor HTTP/1.1\r\n";
    postRequest += "Host: greenhouse-school.herokuapp.com\r\n";
    postRequest += "Authorization: Basic Z3JlZW5ob3VzZTpzdHJvbmdwYXNzd29yZA==\r\n";
    postRequest += "Content-Type: application/json\r\n";
    postRequest += "Content-Length: " + String(postContent.length()) + "\r\n";
    postRequest += "\r\n" + postContent;
    clientPost.print(postRequest);
    lastSendSensor = millis();
  } else {
    // if you didn't get a connection to the server:
    Serial.println("connection failed");
  }
  clientPost.flush();
}

void httpGetSetPoints() {
  // close any connection before send a new request.
  clientGet.stop();
  if (clientGet.connect(server, 80)) {
    Serial.println("connected");
    clientGet.println("GET /set-points HTTP/1.1");
    clientGet.println("Host: greenhouse-school.herokuapp.com");
    clientGet.println("Connection: close");
    clientGet.println();
    lastGetSetPoints = millis();
  }
  else {
    // if you didn't get a connection to the server:
    Serial.println("connection failed");
  }
  clientGet.flush();
}

// Prepocessing function
void jsonSetPointsProcessing(String response) {
  int iStart = response.indexOf('{');
  int iEnd = response.indexOf('}');
  
  if(iStart == -1)
    return;
    
  String json = response.substring(iStart + 1, iEnd);
  int count = 0;
  char jsonArray[json.length()];
  json.toCharArray(jsonArray, json.length());
  for (int i = 0; i < json.length(); i++) {
    if (jsonArray[i] == ':') {
      count++;
      if (count == 2) {
        tSP = ((jsonArray[i + 1] - '0') * 10 + (jsonArray[i + 2] - '0'));
      }
      if (count == 3) {
        hSP = ((jsonArray[i + 1] - '0') * 10 + (jsonArray[i + 2] - '0'));
        break;
      }
    }
  }
}

void loop() {
  
  if (clientGet.available()) {
    String response = clientGet.readString();
    Serial.println(response);
    jsonSetPointsProcessing(response);
  }

  if (clientPost.available()) {
    String response = clientPost.readString();
    Serial.println(response);
  }

  if (millis() - lastSendSensor > postSensorInterval) {
    httpPostSensor();
  }
  
//  if (millis() - lastGetSetPoints > getSetPointsInterval) {
//    httpGetSetPoints();
//  }

  int h = dht.readHumidity();
  int t = dht.readTemperature();
  Serial.print("Temperature ");
  Serial.println(t);
  Serial.print("tSP ");
  Serial.println(tSP);

  Serial.print("Humidity ");
  Serial.println(h);
  Serial.print("hSP ");
  Serial.println(hSP);

  if ( t < tSP - 1) {
    digitalWrite(8, HIGH);
  }
  if ( t >= tSP + 1) {
    digitalWrite(8, LOW);
  }

  if (h <= hSP - 1) {
    digitalWrite(3, LOW);
    digitalWrite(2, HIGH);
  }

  if (h >= hSP + 1) {
    digitalWrite(3, HIGH);
    digitalWrite(2, LOW);
  }

  delay(1000);
}

