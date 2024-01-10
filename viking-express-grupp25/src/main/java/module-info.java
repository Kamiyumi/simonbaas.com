module ht23.sysa21.isp.ht23.sysa21.grupp25 {
    
    exports se.lu.ics.main;

    exports se.lu.ics.models;

    exports se.lu.ics.data;

    requires javafx.fxml;

    requires javafx.controls;

    requires java.sql;

    requires transitive javafx.graphics;

    opens se.lu.ics.controllers to javafx.fxml;

    opens se.lu.ics.main to javafx.graphics;
}
