/* ******************************************************
 * Simulator alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: alpha/Main.java 2015-03-09 buixuan.
 * ******************************************************/
package alpha;

import data.Mod;
import javafx.scene.control.Alert;
import tools.HardCodedParameters;
import specifications.DataService;
import specifications.EngineService;
import specifications.ViewerService;
import data.Data;
import engine.Engine;
import userInterface.Viewer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class Main extends Application {
    // ---HARD-CODED-PARAMETERS---//
    private static String fileName = HardCodedParameters.defaultParamFileName;

    // ---VARIABLES---//
    private static DataService data;
    private static EngineService engine;
    private static ViewerService viewer;

    private Timeline timeline;
    private AnimationTimer timer;

    // ---EXECUTABLE---//
    public static void main(String[] args) {
    	switch(args[0]){
            case "standard":
                data = new Data(Mod.STANDARD);
                break;

            case "circle":
                data = new Data(Mod.CIRCLE);
                break;

            default :
                data = new Data(Mod.STANDARD);
        }

        engine = new Engine();
        viewer = new Viewer();

        ((Engine) engine).bindDataService(data);
        ((Viewer) viewer).bindReadService(data);
        ((Viewer) viewer).bindStartEngineService(engine);

        engine.init();
        viewer.init();
        viewer.startViewer();

        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(((Viewer) viewer).getPanel());
        stage.setScene(scene);
        stage.setWidth(HardCodedParameters.defaultWidth);
        stage.setHeight(HardCodedParameters.defaultHeight);

        // --- Capture les clics de la souris --- //
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(data.getWinner() == null) {
                	viewer.setCursorPressed(event.getX(), event.getY());
                }
            }
        });
        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(data.getWinner() == null) {
                	viewer.setCursorMoved(event.getX(), event.getY());
                }
            }
        });

        stage.show();

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);

        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                scene.setRoot(((Viewer) viewer).getPanel());
            }
        };

        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(HardCodedParameters.enginePaceMillis)));
        timeline.play();
        timer.start();
    }

    // ---ARGUMENTS---//
    private static void readArguments(String[] args) {
        if (args.length > 0 && args[0].charAt(0) != '-') {
            System.err.println("Syntax error: use option -h for help.");
            return;
        }
        for (int i = 0; i < args.length; i++) {
            if (args[i].charAt(0) == '-') {
                if (args[i + 1].charAt(0) == '-') {
                    System.err.println("Option " + args[i] + " expects an argument but received none.");
                    return;
                }
                switch (args[i]) {
                    case "-inFile":
                        fileName = args[i + 1];
                        break;
                    case "-h":
                        System.out.println("Options:");
                        System.out.println(
                                " -inFile FILENAME: (UNUSED AT THE MOMENT) set file name for input parameters. Default name is"
                                        + HardCodedParameters.defaultParamFileName + ".");
                        break;
                    default:
                        System.err.println("Unknown option " + args[i] + ".");
                        return;
                }
                i++;
            }
        }
    }
}
