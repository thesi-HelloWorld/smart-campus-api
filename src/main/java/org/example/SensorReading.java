package org.example;

public class SensorReading {

        private String id;
        private long timestamp;
        private double value;

        public SensorReading(){
        }

        public SensorReading(String id, long timestamp, double value){
            this.id = id;
            this.timestamp = timestamp;
            this.value = value;
        }

        public String getId(){
            return id;
        }

        public long getTimestamp(){
            return timestamp;
        }

        public double getValue(){
            return value;
        }
}
