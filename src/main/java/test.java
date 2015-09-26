/**
 * Created by http://www.hascode.com/2013/05/creating-rich-clients-with-apache-pivot/
 * Edited by Will on 9/25/2015.
 */
import java.io.File;
import java.io.IOException;

import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.Map;
import org.apache.pivot.collections.Sequence;
import org.apache.pivot.wtk.Alert;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.FileBrowserSheet;
import org.apache.pivot.wtk.Form;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.ListView;
import org.apache.pivot.wtk.MessageType;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.Sheet;
import org.apache.pivot.wtk.SheetCloseListener;
import org.apache.pivot.wtk.Window;

public class test {
    public static void main(final String[] args) {
        DesktopApplicationContext.main(MyApp.class, args);
    }

    public static class MyApp extends Window implements Application {
        private final Form form = new Form();
        private final Form.Section section = new Form.Section();
        private final Label label = new Label("Select file to upload...");

        private final Form.Section section2 = new Form.Section();
        private final Label label2 = new Label("test");
        private final PushButton testButton = new PushButton();

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

            section.add(label2);
            testButton.setButtonData("test");
            section.add(testButton);
            form.getSections().add(section2);

            testButton.getButtonPressListeners().add(testButtonPressListener);

            fileBrowser.setMode(FileBrowserSheet.Mode.SAVE_AS);
            btOpenFileDialog.getButtonPressListeners().add(
                    fileDialogDisplayListener);
            this.setContent(form);
            this.setTitle("I have no clue what I am doing.");
            this.setMaximized(true);
        }

        private final ButtonPressListener fileDialogDisplayListener = new ButtonPressListener() {
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

        private final ButtonPressListener testButtonPressListener = new ButtonPressListener() {
            @Override
            public void buttonPressed(final Button testButton) throws RuntimeException{
                try {
                    Runtime.getRuntime().exec("explorer.exe /select," + "C:/Windows");
                } catch (IOException e) {
                    e.printStackTrace();
                }
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