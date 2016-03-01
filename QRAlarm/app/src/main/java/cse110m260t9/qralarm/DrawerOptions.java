package cse110m260t9.qralarm;

/**
 * Created by cchul on 2/29/2016.
 * This class will be used to edit options inside the navigation drawer in main activity by using
 * an enum list.
 */
public class DrawerOptions {

    public enum EOptions {
        E_OPTIONS_SET_HOME("Set Home Location", 0),
        E_OPTIONS_SHOW_HOME("Show Home Location", 1);

        private final String text;
        private final int id;

        EOptions(final String text, int id) {
            this.text = text;
            this.id = id;
        }

        @Override
        public String toString() {
            return text;
        }

        public int getId(){
            return id;
        }

        public static final int size = EOptions.values().length;

    }
}
