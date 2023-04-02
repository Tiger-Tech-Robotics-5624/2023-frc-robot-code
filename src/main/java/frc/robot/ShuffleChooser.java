package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class ShuffleChooser {
    private SendableChooser<String> start;
    private SendableChooser<Boolean> balance;
    
    public ShuffleChooser() {
        start = new SendableChooser<>();
        balance = new SendableChooser<>();


    } 

    public void configStartScore() {
        start.addOption("Cube","CUBE");
        start.addOption("Cone", "CONE");
    }

    public void configureLocation() {
        balance.addOption("Mid",true);
        balance.addOption("Sides", false);
    }

    public SendableChooser<String> getStartItem() {
        return this.start;
    }

    public SendableChooser<Boolean> getStartLocation() {
        return this.balance;
    }
}
