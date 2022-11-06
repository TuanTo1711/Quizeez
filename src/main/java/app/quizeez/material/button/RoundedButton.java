package app.quizeez.material.button;

import com.formdev.flatlaf.extras.components.FlatButton;

public class RoundedButton extends FlatButton{

    public RoundedButton() {
        setOpaque(false);
        setButtonType(FlatButton.ButtonType.roundRect);
    }
    
}
