package net.fachtnaroe.webviewstringchangedemo;

import android.content.Intent;
import android.net.Uri;

import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.HorizontalArrangement;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.TextBox;
import com.google.appinventor.components.runtime.VerticalArrangement;

public class MainActivity extends Form implements HandlesEventDispatching {

    private
    Button sendButton;
    Label rxLabel, txLabel, heading;
    TextBox rxTextBox, txTextBox, dbgLabel;
    HorizontalArrangement main;
    VerticalArrangement androidDisplay;
    fachtnaWebViewer htmlDisplay;

    protected void $define() {
        this.Sizing("Responsive");
        this.BackgroundColor(0xFF167340);
        main = new HorizontalArrangement(this);
        main.HeightPercent(100);
        main.WidthPercent(100);

        htmlDisplay=new fachtnaWebViewer(main);
        htmlDisplay.WidthPercent(50);
        htmlDisplay.HeightPercent(100);
        htmlDisplay.HomeUrl("file:///android_asset/guestpage.html");
        htmlDisplay.WebViewString("htmlDisplay_started");
        htmlDisplay.GoHome();

        androidDisplay = new VerticalArrangement(main);
        androidDisplay.WidthPercent(50);
        androidDisplay.HeightPercent(100);

        heading = new Label(androidDisplay);
        heading.Text("android");
        heading.TextColor(Component.COLOR_WHITE);
        heading.FontSize(45);
        rxLabel = new Label(androidDisplay);
        rxLabel.Width(Component.LENGTH_FILL_PARENT);
        rxLabel.Text("Receive");
        rxLabel.TextColor(Component.COLOR_WHITE);
        rxLabel.FontSize(20);
        rxLabel.FontBold(true);
        rxTextBox=new TextBox(androidDisplay);
        rxTextBox.Width(Component.LENGTH_FILL_PARENT);
        rxTextBox.BackgroundColor(Component.COLOR_WHITE);
        rxTextBox.Height(50);

        txLabel = new Label(androidDisplay);
        txLabel.Width(Component.LENGTH_FILL_PARENT);
        txLabel.Text("\nSend");
        txLabel.TextColor(Component.COLOR_WHITE);
        txLabel.FontSize(20);
        txLabel.FontBold(true);
        txTextBox=new TextBox(androidDisplay);
        txTextBox.Width(Component.LENGTH_FILL_PARENT);
        txTextBox.Text("testing android");
        txTextBox.BackgroundColor(Component.COLOR_WHITE);
        txTextBox.Height(50);

        sendButton=new Button(androidDisplay);
        sendButton.Width(Component.LENGTH_FILL_PARENT);
        sendButton.Text("Send");

        dbgLabel=new TextBox(androidDisplay);
        dbgLabel.Width(Component.LENGTH_FILL_PARENT);
        dbgLabel.BackgroundColor(Component.COLOR_WHITE);
        dbgLabel.Height(50);
        dbgLabel.Text("---");


        EventDispatcher.registerEventForDelegation(this, formName, "Click");
        EventDispatcher.registerEventForDelegation(this, formName, "fachtnaWebViewStringChange");
        EventDispatcher.registerEventForDelegation(this, formName, "WebViewStringChange");
    }

    public boolean dispatchEvent(Component component, String componentName, String eventName, Object[] params) {

        dbg("dispatchEvent: " + formName + " [" +component.toString() + "] [" + componentName + "] " + eventName);
        if (eventName.equals("Click")) {
            if (component.equals(sendButton)) {
                sendButton_Clicked();
                return true;
            }
        }
        else if( eventName.equals("fachtnaWebViewStringChange") ) {
            if (component.equals(htmlDisplay)) {
                htmlDisplay_StringChange(htmlDisplay.WebViewString());
                return true;
            }
        }
        return false;
    }
    public void sendButton_Clicked(){
        htmlDisplay.WebViewString( txTextBox.Text() );
        htmlDisplay.
        dbg("Sending: "+txTextBox.Text() );
        dbgLabel.Text(txTextBox.Text());
    }

    public void htmlDisplay_StringChange(final String theString) {
        rxTextBox.Text(theString);
        if (theString.equals("update")) {
            // https://stackoverflow.com/questions/5161951/android-only-the-original-thread-that-created-a-view-hierarchy-can-touch-its-vi
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=net.fachtnaroe.tuuber"));
            startActivity(browserIntent);
            finishActivityWithTextResult("");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Stuff that updates the UI
                    //
//                    webview_Message = new fachtnaWebViewer(MainMenu);
//                    webview_Message.HomeUrl(
//                            "https://play.google.com/store/apps/details?id=net.fachtnaroe.tuuber"
//                    );
//
//                    webview_Message.WidthPercent(100);
//                    webview_Message.HeightPercent(100);
//                    webview_Message.GoHome();

//                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(base_url+theString));
//                    startActivity(browserIntent);
                }
            });

        }

    }

    public static void dbg (String debugMsg) {
        System.err.print( "~~~> " + debugMsg + " <~~~\n");
    }
}
