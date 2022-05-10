package com.mycompany.popovertest;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.ToggleSwitch;

/**
 * JavaFX App
 */
public class App extends Application {

    private final Button btn = new Button("Hover this button to see the popover!");
    private final CheckBox cbSecondPO = new CheckBox("show second PopOver");
    private final BooleanProperty showSecond = new SimpleBooleanProperty();
    private static final String PO_TXT = "If you close the Window using the closebutton of the window, while the Popover is visible, the Application will exit with an exception!";
    private static final Label LBL_PO = new Label(PO_TXT), LBL_PO1 = new Label(PO_TXT);

    private final PopOver po = new PopOver(LBL_PO);
    private final PopOver po1 = new PopOver(LBL_PO1);

    @Override
    public void start(Stage stage) {
        formatPOText(LBL_PO);
        formatPOText(LBL_PO1);
        showSecond.bindBidirectional(cbSecondPO.selectedProperty());
        po1.setArrowLocation(PopOver.ArrowLocation.RIGHT_TOP);
        initMouseInteraction();
        initSetupDialog();
        final VBox vBox = new VBox(cbSecondPO, btn);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        var scene = new Scene(vBox, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public void initSetupDialog() {
        Dialog dlg = new Alert(Alert.AlertType.CONFIRMATION, "(Will fix the exception-problem)", ButtonType.YES, ButtonType.NO);
        dlg.setHeaderText("Continue with safety net?");
        dlg.showAndWait().ifPresent((t) -> {
            if (dlg.getResult() instanceof ButtonType btn) {
                if (btn.equals(ButtonType.YES)) {
                    setUpOwnerCloseBehaviour(po);
                    setUpOwnerCloseBehaviour(po1);
                }
            }
        });
    }

    public void initMouseInteraction() {
        btn.setOnMouseEntered(t -> {
            po.show(btn);
            if (showSecond.get()) {
                po1.show(btn);
            }
        });
    }

    private final EventHandler<WindowEvent> ownerCloseLambda = (t) -> {
        po.hide(Duration.ZERO);
        po1.hide(Duration.ZERO);
    };

    private final InvalidationListener wdwLstner = (t) -> {
        if (po.getOwnerWindow() != null) {
            po.getOwnerWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, ownerCloseLambda);
            po.getOwnerWindow().addEventFilter(WindowEvent.WINDOW_HIDING, ownerCloseLambda);
        }
    };

    private void setUpOwnerCloseBehaviour(PopOver po) {
        po.ownerWindowProperty().addListener(wdwLstner);
    }

    private void formatPOText(Label lbl) {
        lbl.setWrapText(true);
        lbl.setStyle("-fx-padding: 7;");
        lbl.setPrefSize(300, Region.USE_COMPUTED_SIZE);
    }

    public static void main(String[] args) {
        launch();
    }

}
