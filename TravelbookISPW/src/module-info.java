module travelbook {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
	requires javafx.swing;
	requires java.sql;
	requires json.simple;
	requires mysql.connector.java;
	requires java.mail;
	requires httpclient;
	requires javafx.web;
	requires junit;

    opens main.java.travelbook.view to javafx.fxml;
    exports main.java.travelbook;
    exports main.java.travelbook.view;
    exports main.java.travelbook.util;
}