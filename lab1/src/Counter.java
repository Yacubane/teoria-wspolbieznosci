public class Counter {

    private int value;

    public Counter() {

    }

    public void increment() {
        value++;
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void decrement() {
        value--;
    }

    public int getValue() {
        return value;
    }

}
