/**
 * Created by http://www.hascode.com/2013/05/creating-rich-clients-with-apache-pivot/
 * Edited by Will on 9/25/2015.
 */
import java.io.File;

import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.Map;
import org.apache.pivot.collections.Sequence;
import org.apache.pivot.wtk.*;

public class test {
    public static void main(final String[] args) {
        DesktopApplicationContext.main(MyApp.class, args);
    }

    public static class MyApp extends Window implements Application {
        private final Form form = new Form();
        private final Form.Section section = new Form.Section();
        private final Label label = new Label("Select file to upload...");

        private final PushButton btOpenFileDialog = new PushButton();
        private final FileBrowserSheet fileBrowser = new FileBrowserSheet();

        public MyApp() {
            compose();
        }

        private void compose() {
            section.add(label);
            btOpenFileDialog.setButtonData("Select File..");
            section.add(btOpenFileDialog);
            form.getSections().add(section);


            fileBrowser.setMode(FileBrowserSheet.Mode.SAVE_AS);
            btOpenFileDialog.getButtonPressListeners().add(
                    buttonListener);
            this.setContent(form);
            this.setTitle("I have no clue what I am doing.");
            this.setMaximized(true);
        }

        private final ButtonPressListener buttonListener = new ButtonPressListener() {
            @Override
            public void buttonPressed(final Button button) {
                fileBrowser.open(MyApp.this, new SheetCloseListener() {
                    @Override
                    public void sheetClosed(final Sheet sheet) {
                        if (sheet.getResult()) {
                            Sequence<File> selectedFiles = fileBrowser
                                    .getSelectedFiles();

                            ListView listView = new ListView();
                            listView.setListData(new ArrayList<File>(
                                    selectedFiles));
                            listView.setSelectMode(ListView.SelectMode.NONE);
                            listView.getStyles().put("backgroundColor", null);

                            Alert.alert(MessageType.INFO,
                                    "Files selected for upload:", listView,
                                    MyApp.this);
                        } else {
                            Alert.alert(MessageType.INFO,
                                    "No files selected for upload.", MyApp.this);
                        }
                    }
                });
            }
        };

        @Override
        public void startup(final Display display,
                            final Map<String, String> properties) throws Exception {
            this.open(display);
        }

        @Override
        public boolean shutdown(final boolean optional) throws Exception {
            this.close();
            return false;
        }

        @Override
        public void suspend() throws Exception {
        }

        @Override
        public void resume() throws Exception {
        }
    }
}