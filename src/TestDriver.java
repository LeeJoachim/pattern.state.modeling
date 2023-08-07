package state;

class Machine {

    int coinNum = 0;
    State state;

    public Machine() {
        state = new NoCoin(this);
    }
    public void setState(State state) {
        this.state = state;
    }

    public void inputCoin() {
        state.inputCoin();
    }
    public void outputCoin() {
        state.outputCoin();
    }
    public void turningCrank() {
        state.turningCrank();
    }
    public void dispense() {
        state.dispense();
    }

    boolean hasCoinEnogh() {   
        if(coinNum >= 2) {
            return true;
        } else {
            return false;
        }
    }
    void plusCoin() {
        coinNum++;
        System.out.println("number of coin : " + coinNum);
    }
    void minusCoin(int n) {
        coinNum -= n;
        System.out.println("number of coin : " + coinNum);
    }
    boolean hasCoinEmpty() {
        if(coinNum == 0) {
            return true;
        } else {
            return false;
        }
    }
    void setCoinZero() {
        coinNum = 0;
    }
    void outAllCoins() {
        System.out.println("Coins are all returned");
        coinNum = 0;
    }

}
abstract class State {

    Machine machine;

    void inputCoin() {
            machine.plusCoin();

        if (machine.hasCoinEnogh()) {
            machine.setState(new HasCoin(machine)); 
        }
            
    }
    void outputCoin() {
        if (machine.hasCoinEmpty()) {
            System.out.println("ERR : No Coin");
            machine.setState(new NoCoin(machine));
        } else {
            machine.outAllCoins();
            machine.setState(new NoCoin(machine));
        }
    }

    abstract void turningCrank();
    abstract void dispense();

    
}

class NoCoin extends State {

    public NoCoin(Machine machine) {
        this.machine = machine;
    }
    void turningCrank() {
        System.out.println("ERR : No Coin");
    }
    void dispense() {
        System.out.println("ERR : No Coin");
    }

}

class HasCoin extends State {

    public HasCoin(Machine machine) {
        this.machine = machine;
    }

    void turningCrank() {
        System.out.println("Crank turned");
        int random = (int)(Math.random() * 10); // 0 ~ 9
        if (random == 0) {
            System.out.println("congratulations! you got one more chance!");
            machine.plusCoin();
            machine.plusCoin();
        }
        machine.setState(new Sold(machine));
    }
    void dispense() {
        System.out.println("ERR : Crank not turned");
    }
}

class Sold extends State {

    public Sold(Machine machine) {
        this.machine = machine;
    }
    
    @Override
    void inputCoin() {
        System.out.println("ERR : can't");
    }
    void outputCoin() {
        System.out.println("ERR : can't");
    }
    void turningCrank() {
        System.out.println("ERR : can't");
    }
    void dispense() {
        System.out.println("dispensing... Here this is your candy!");
        machine.minusCoin(2);

        if (machine.hasCoinEnogh()) {
            machine.setState(new HasCoin(machine));
        } else {
            machine.setState(new NoCoin(machine));
        }
    }

}

class SoldOut extends State {

    public SoldOut(Machine machine) {
        this.machine = machine;
    }

    @Override
    void inputCoin() {
        System.out.println("sold out");
    }
    void outputCoin() {
        System.out.println("sold out");
    }
    void turningCrank() {
        System.out.println("sold out");
    }
    void dispense() {
        System.out.println("sold out");
    }

}

public class TestDriver {
    public static void main(String[] args) {

        Machine machine = new Machine();

        machine.inputCoin();
        machine.inputCoin();
        machine.turningCrank();
        machine.dispense();

        machine.inputCoin();
        machine.outputCoin();
        machine.turningCrank();
        
    }
}
