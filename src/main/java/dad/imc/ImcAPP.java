package dad.imc;


import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class ImcAPP extends Application {

	/*private IntegerProperty peso = new SimpleIntegerProperty();
	private IntegerProperty altura = new SimpleIntegerProperty();*/
	private TextField pesoText;
	private TextField alturaText;
	private Label imcLabel;
	private Label resultadoLabel;
	
	private StringProperty bindeoResult = new SimpleStringProperty();
	private DoubleProperty bindeoimc = new SimpleDoubleProperty();
	
	Double alturaMetros = 0.0;
	@Override
	public void start(Stage primaryStage) throws Exception {
		//inicializa los objetos
		pesoText = new TextField();
		alturaText = new TextField();
			
		imcLabel = new Label();
		resultadoLabel = new Label();
		
		//bindeo los label con su property
		resultadoLabel.textProperty().bind(bindeoResult);
		imcLabel.textProperty().bindBidirectional(bindeoimc, new NumberStringConverter());//va a recibir numeros
		
		
		//listener para peso
		pesoText.textProperty().addListener((o, ov, nv) -> {
			if (pesoText.getText().isEmpty()) {
				bindeoResult.setValue("Sin datos");
				bindeoimc.setValue(0);
			}
			else {
				//segundo listener para altura
				alturaText.textProperty().addListener((ob, ovb, nvb) -> {
					if (alturaText.getText().isEmpty()) {
						bindeoResult.setValue("Sin datos");
						bindeoimc.setValue(0);
					}
					else {
						//paso la altura a metros
						alturaMetros = (Double.parseDouble(alturaText.getText()))/100.0;
						
						//formula: IMC = Peso/altura^2
						bindeoimc.setValue((Double.parseDouble(pesoText.getText())/Math.pow(alturaMetros, 2))
								);
						
						// Clasificacion
						if (bindeoimc.getValue()<18.5) {
							bindeoResult.setValue("Bajo peso");
						}else if (bindeoimc.getValue()>=18.5 & bindeoimc.getValue()<25) {
							bindeoResult.setValue("Normal");
						}else if (bindeoimc.getValue()>=25 & bindeoimc.getValue()<30) {
							bindeoResult.setValue("Sobre Peso");
						}else {
							bindeoResult.setValue("Obeso");
						}
					}
					
				});
			}
			
		});
		
		// aspectos visuales
		HBox primerBox = new HBox();
		primerBox.setSpacing(5);
		primerBox.setAlignment(Pos.CENTER);
		primerBox.getChildren().addAll(new Label(" Peso: "), pesoText, new Label("KG"));

		HBox segundoBox = new HBox();
		segundoBox.setSpacing(5);
		segundoBox.setAlignment(Pos.CENTER);
		segundoBox.getChildren().addAll(new Label(" Altura: "), alturaText, new Label("CM"));

		HBox tercerBox = new HBox();
		tercerBox.setSpacing(5);
		tercerBox.setAlignment(Pos.CENTER);
		tercerBox.getChildren().addAll(new Label("IMC: "), imcLabel);
		
		VBox root = new VBox();
		root.setSpacing(5);
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(primerBox, segundoBox, tercerBox, resultadoLabel);
		
		Scene scene = new Scene(root, 420, 200);

		primaryStage.setTitle("IMC");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		

	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	

}


