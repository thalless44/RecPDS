package util;

import java.util.function.UnaryOperator;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;

public class LimitarCaracter {

    public enum TipoEntrada {
         NOME, EMAIL, FONE;
    }

    private int qtdCaracteres;
    private TipoEntrada tpEntrada;

    public LimitarCaracter(int qtdCaracteres, TipoEntrada tpEntrada) {
        this.qtdCaracteres = qtdCaracteres;
        this.tpEntrada = tpEntrada;
    }

    public TextFormatter<String> getFormatter() {
        return new TextFormatter<>(new UnaryOperator<TextFormatter.Change>() {
            @Override
            public TextFormatter.Change apply(TextFormatter.Change change) {
                String newText = change.getControlNewText();
                
                if (newText.length() > qtdCaracteres) {
                    return null;
                }
                
                String insertedText = change.getText();
                
                switch (tpEntrada) {
                    
                        
                    case NOME:
                        if (!insertedText.matches("[\\p{IsLatin} ]*")) return null;
                        break;
                        
                    case EMAIL:
                        if(insertedText.matches("^[^\\s@]+@gmail\\.com$")) return null;
                        
                        break;
                }
                
                return change;
            }
        });
    }
    

    public void applyToTextInputControl(TextInputControl textInputControl) {
        textInputControl.setTextFormatter(getFormatter());
    }
}