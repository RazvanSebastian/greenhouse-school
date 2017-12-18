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
unsigned long lastConnectionTime = 0;
// 10 sec delay between updates, in milliseconds (L used for long type)
const unsigned long postingInterval = 10L * 1000L;

//======Sensor======//
// what digital pin we're connected to
#define DHTPIN 7
// Sensor version ;DHT 11
#define DHTTYPE DHT11
// Initialize DHT sensor.
DHT dht(DHTPIN, DHTTYPE);

void setup() {
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
  client.stop();
  if (client.connect(server, 80)) {
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
    postRequest += "Content-Type: application/json\r\n";
    postRequest += "Content-Length: " + String(postContent.length()) + "\r\n";
    postRequest += "\r\n" + postContent;
    client.print(postRequest);
    lastConnectionTime = millis();
  } else {
    // if you didn't get a connection to the server:
    Serial.println("connection failed");
  }

}

void loop() {
  if (client.available()) {
    char c = client.read();
    Serial.write(c);
  }

  // at every 10 sec a request is made
  if (millis() - lastConnectionTime > postingInterval) {
    httpPostSensor();
  }
}

