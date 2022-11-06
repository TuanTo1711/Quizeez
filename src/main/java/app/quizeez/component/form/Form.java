package app.quizeez.component.form;

import javax.swing.JPanel;

public abstract class Form extends JPanel{

    public Form() {
        setOpaque(false);
    }
    
    public abstract void refresh ();
}
