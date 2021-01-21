/*PongFX

Versión del clásico juego pong, utilizado como parte del aprendizaje de rogramación
de Java en el Ciclo Formativo de Grado Supperio de Desarrollo de Aplicacions Web

Diego Jesús Sánchez Del Corral
2021*/
package diegosanchez.pongfx.dj;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.UP;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {
        
        //Variables para lugar de inicio y velocidad de la bola
        int ballCenterX = 300;
        int ballCurrentSpeedX = 3;
        int ballCenterY = 200;
        int ballCurrentSpeedY = 3; 
        //Constantes para el tamaño de la escena
        final int SCENE_TAM_X = 600;
        final int SCENE_TAM_Y = 400;
        //Constantes para las medidas de la pala
        final int STICK_WIDTH = 7;
        final int STICK_HEIGHT = 50;
        //Variable para lugar de inicio de la pala
        int stickPosy = (SCENE_TAM_Y - STICK_HEIGHT) / 2;
        int stickCurrentSpeed = 0;
        //Constante para el tamaño del texto
        final int TEXT_SIZE =24;
        //Variable puntuacion actual
        int score;
        //Variable para puntuación actual
        Text textScore;
        //Varable para puntuación máxima
        int highScore;
        
       

    @Override
    public void start(Stage primayStage) {
        
        //Reiniciar partida
        //resetGame();
        //Crear el contenedor para poner los objetos
        Pane root = new Pane();
        //Crear la escena(ventana)
        Scene scene = new Scene(root, SCENE_TAM_X, SCENE_TAM_Y, Color.BLACK);
        primayStage.setTitle("PongFX");
        primayStage.setScene(scene);
        primayStage.show();
        //Crear la bola con la posición, el radio y el relleno
        Circle circleBall = new Circle();
        circleBall.setCenterX(ballCenterX);
        circleBall.setCenterY(ballCenterY);
        circleBall.setRadius(7);
        circleBall.setFill(Color.WHITE);
        //Agregar la bola al contenedor
        root.getChildren().add(circleBall);
        //Crear el rectangulo con la posición relativa, ancho, alto y relleno
        Rectangle rectStick = new Rectangle(SCENE_TAM_X*0.9,stickPosy,STICK_WIDTH,STICK_HEIGHT);
        rectStick.setFill (Color.WHITE);
        //Agregar el rectangulo al contenedor
        root.getChildren().add(rectStick);
        //Llamada al método intersect
        Shape.intersect(circleBall, rectStick);
        //Crear la linea de la red con bucle for
        for(int i=0; i<SCENE_TAM_Y; i+=30) {
            Line line = new Line (SCENE_TAM_X/2, i, SCENE_TAM_X/2, i+10);
            line.setStroke(Color.WHITE);
            line.setStrokeWidth(4);
            root.getChildren().add(line);
        }   
        //Crear los marcadores con Layout
        //Layout principal
        HBox paneScores = new HBox();
        paneScores.setTranslateY(20);
        paneScores.setMinWidth(SCENE_TAM_X);
        paneScores.setAlignment(Pos.CENTER);
        paneScores.setSpacing(100);
        root.getChildren().add(paneScores);
        //Layout para puntuación actual
        HBox paneCurrentScore = new HBox();
        paneCurrentScore.setSpacing(10);
        paneScores.getChildren().add(paneCurrentScore);
        //Layout para puntuación máxima
        HBox paneHighScore = new HBox();
        paneHighScore.setSpacing(10);
        paneScores.getChildren().add(paneHighScore);
        //Texo de etiqueta para la puntuación
        Text textTitleScore = new Text ("Score:");
        textTitleScore.setFont(Font.font(TEXT_SIZE));
        textTitleScore.setFill(Color.WHITE);
        //Texo para la puntuación
        textScore = new Text ("0");
        textScore.setFont(Font.font(TEXT_SIZE));
        textScore.setFill(Color.WHITE);
        //Texto de etiqueta para la puntuación máxima
        Text textTitleHighScore = new Text("Max.Score:");
        textTitleHighScore.setFont(Font.font(TEXT_SIZE));
        textTitleHighScore.setFill(Color.WHITE);
        //Texto para la puntuación máxima
        Text textHighScore = new Text("0");
        textHighScore.setFont(Font.font(TEXT_SIZE));
        textHighScore.setFill(Color.WHITE);
        //Añadir los textos a los Layout reservados para ello
        paneCurrentScore.getChildren().add(textTitleScore);
        paneCurrentScore.getChildren().add(textScore);
        paneHighScore.getChildren().add(textTitleHighScore);
        paneHighScore.getChildren().add(textHighScore);
        
       
             
        //Movimiento de la bola
        Timeline animationBall = new Timeline(
            //Movimiento de la bola y rebote en los laterales
            new KeyFrame(Duration.seconds(0.017),(var ae) -> {
                circleBall.setCenterX(ballCenterX);
                ballCenterX+= ballCurrentSpeedX;
                //Comprobar si la bola ha tocado el lado derecho
                if(ballCenterX >= SCENE_TAM_X) {
                    //comprobar si hay una nueva puntación mas alta
                    if (score > highScore) {
                        //Cambiar la nueva puntuación
                        highScore = score;
                        textHighScore.setText(String.valueOf(highScore));
                    }
                    //Reiniciar partida
                    resetGame();
                }
                if(ballCenterX <= 0) {
                    ballCurrentSpeedX = 3;
                }
                circleBall.setCenterY(ballCenterY);
                ballCenterY += ballCurrentSpeedY;
                if(ballCenterY >= SCENE_TAM_Y) {
                    ballCurrentSpeedY = -3;
                }
                if(ballCenterY <= 0) {
                    ballCurrentSpeedY = 3;
                }
                //Actualizar posición de la pala
                stickPosy += stickCurrentSpeed;
                //Sentencia para no salirse la pala del borde superio
                if (stickPosy < 0) {
                    stickPosy = 0;
                } else {
                    //Sentencia para no salirse la pala por el borde inferior
                    if (stickPosy > SCENE_TAM_Y - STICK_HEIGHT) {
                        stickPosy = SCENE_TAM_Y - STICK_HEIGHT ;
                    }
                }
                rectStick.setY(stickPosy);
                //Sentencia para comprobar si hay colición entre las formas
                Shape shapeColision = Shape.intersect(circleBall, rectStick);
                //Variable para csaber si hay colición
                boolean colisionVacia = shapeColision.getBoundsInLocal().isEmpty();
                //Sentencia para saber si colicionan la pala y la bola
                if (colisionVacia == false && ballCurrentSpeedX > 0) {
                    ballCurrentSpeedX = -3;
                    //Incrementar puntuación
                    score++;
                    textScore.setText(String.valueOf(score));
                 
                    
                }
            })
        );
            
        animationBall.setCycleCount (Timeline.INDEFINITE);
        animationBall.play();
        
        //Detección de las pulsaciones de las teclas
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch(event.getCode()) {
                case UP:
                    //Pulsada tecla arriba
                    stickCurrentSpeed = -6;
                    break;
                case DOWN:
                    //Pulsada tecla abajo
                    stickCurrentSpeed = 6;
                    break;
            }
        });
        //Detección de dejar de pulsar las teclas
        scene.setOnKeyReleased((KeyEvent event) -> {
            stickCurrentSpeed = 0;
        });
        
        
    }
    //nueva método para reiniciar el juego
    private void resetGame() {
        score = 0;
        textScore.setText(String.valueOf(score));
        ballCenterX = 10;
        ballCurrentSpeedY = 3;
        //Posición inicial aleatoria para la bola en el eje Y
        Random random = new Random();
        ballCenterY = random.nextInt(SCENE_TAM_Y);
    }
    public static void main(String[] args) {
        launch();
    }
}