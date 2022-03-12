<?xml version="1.0" encoding="UTF-8"?>

<?import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.TableColumn?>
<?import javafx.scene.control.TableView?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.text.Font?>

<AnchorPane prefHeight="619.0" prefWidth="900.0" xmlns="http://javafx.com/javafx/17" xmlns:fx="http://javafx.com/fxml/1"
            fx:controller="com.example.finalprojdb.Patient2_Controller">
    <children>
        <ImageView fitHeight="356.0" fitWidth="298.0" layoutX="1238.0" layoutY="443.0" pickOnBounds="true" preserveRatio="true" AnchorPane.bottomAnchor="0.993011474609375" AnchorPane.leftAnchor="1238.0" AnchorPane.rightAnchor="1.599999999999909" AnchorPane.topAnchor="443.0">
            <image>
                <Image url="https://cdn2.vectorstock.com/i/1000x1000/93/11/doctor-woman-and-patient-vector-21229311.jpg" />
            </image>
        </ImageView>
        <Label layoutX="230.0" layoutY="5.0" prefHeight="115.0" prefWidth="1320.0" style="-fx-background-color: #b6e2f6;" text=" Clinic doctors" AnchorPane.bottomAnchor="682.0" AnchorPane.leftAnchor="230.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
            <font>
                <Font name="Arabic Typesetting" size="77.0" />
            </font>
        </Label>
        <TableView fx:id="clinicDTable" layoutX="330.0" layoutY="213.0" prefHeight="467.0" prefWidth="691.0">
            <columns>
                <TableColumn fx:id="doctorNameCol" prefWidth="156.79998779296875" text="Doctor Name" />
                <TableColumn fx:id="doctorIDCol" prefWidth="123.20001220703125" text="Doctor ID" />
                <TableColumn fx:id="specializationCol" minWidth="2.4000244140625" prefWidth="410.4000244140625" text="Specialization" />
            </columns>
        </TableView>
        <fx:include source="PatientMenu.fxml" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="1320.0" AnchorPane.topAnchor="0.0" />
        <HBox layoutX="330.0" layoutY="144.0" prefHeight="50.0" prefWidth="371.0">
            <children>
                <FontAwesomeIconView glyphName="SEARCH" size="40" wrappingWidth="60.74283313751221" />
                <TextField fx:id="tf_search" prefHeight="26.0" prefWidth="238.0" />
            </children>
        </HBox>
    </children>
</AnchorPane>
