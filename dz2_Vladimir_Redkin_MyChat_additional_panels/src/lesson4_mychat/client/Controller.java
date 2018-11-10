package lesson4_mychat.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Controller {

    @FXML
    Button sendButton;

    //    @FXML
//    TextArea textArea;
    @FXML
    Label labelText;
    @FXML
    TextField textField;
    @FXML
    HBox upperPanel;
    @FXML
    HBox bottomPanel;
    @FXML
    TextField loginField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button loginBtn;
    @FXML
    ListView clientList;
    @FXML
    VBox vBoxChat;

    @FXML
    private MenuItem changeNickMenuBtn;

    @FXML
    private MenuItem registerMenuBtn;
    @FXML
    private HBox registerPanel;

    @FXML
    TextField regNickField;
    @FXML
    TextField regLoginField;
    @FXML
    PasswordField regPasswordField;
    @FXML
    HBox changeNickPanel;
    @FXML
    TextField changeNickField;
    @FXML
    Button changeNickBtn;
    @FXML
    ScrollPane scrollPaneChat;


    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    final String IP = "localhost";
    final int PORT = 1889;

    private boolean isAuthorised;
    private boolean regFormVisible = true;
    String nick;

    public void setAuthorised(boolean isAuthorised) {
        this.isAuthorised = isAuthorised;

        if (!isAuthorised) {
            upperPanel.setVisible(true);
            upperPanel.setManaged(true);
            bottomPanel.setVisible(false);
            bottomPanel.setManaged(false);
            clientList.setVisible(false);
            clientList.setManaged(false);
            registerMenuBtn.setVisible(true);
            changeNickMenuBtn.setVisible(false);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    vBoxChat.getChildren().clear();
                }
            });


        } else {
            upperPanel.setVisible(false);
            upperPanel.setManaged(false);
            bottomPanel.setVisible(true);
            bottomPanel.setManaged(true);
            clientList.setVisible(true);
            clientList.setManaged(true);
            registerMenuBtn.setVisible(false);
            changeNickMenuBtn.setVisible(true);

        }
    }


    public void sendMessage() {

        try {
            out.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void connect() {
        try {
            socket = new Socket(IP, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());



            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        while (true) {
                            String str = in.readUTF();
                            if (str.startsWith("/authok")) {
                                String[] tokens = str.split(" ");
                                nick = tokens[1];
                                setAuthorised(true);
                                System.out.println(nick);
                                break;
                            } else if (str.startsWith("/regok")) {
                                System.out.println("registration -ok.");
                                printLeftOrRight("Пользователь добавлен.");
                                regFormVisible = false;
                                registerFormVisible();
                                break;
                            } else {
                                printLeftOrRight(str);
                                out.writeUTF("/end");  // для того чтобы закрылся сокет (иначе кнопка Login перестанет работать стр.145)
                                try {
                                    Thread.sleep(500); // пауза, чтоб успеть прочитать сообщение о неверно введенном пароле
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;

                            }

                        }

                        while (true) {
                            String msg = in.readUTF();
                            if (msg.equals("/serverClosed")) {
                                setAuthorised(false);
                                System.out.println("/serverClosed received");
                                break;
                            } else if (msg.equals("/closePlease")) {
                                System.out.println("/closePlease accepted");
                                out.writeUTF("/end");

                            } else if (msg.startsWith("/clientList")) {
                                String[] tokens = msg.split(" ");
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        clientList.getItems().clear();
                                        for (int i = 1; i < tokens.length; i++) {
                                            clientList.getItems().add(tokens[i]);
                                        }
                                    }
                                });

                            } else {
                                printLeftOrRight(msg);

                            }

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                            out.close();
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printLeftOrRight(String msg) {
        String[] tokens = msg.split(" ", 2);
        Label label = new Label(msg);
        VBox vbox = new VBox();
        System.out.println("Server: " + msg + " " + tokens[0]);

        if (tokens[0].equals(nick)) {
            vbox.setAlignment(Pos.TOP_RIGHT);
        } else {
            vbox.setAlignment(Pos.TOP_LEFT);
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vbox.getChildren().add(label);
                vBoxChat.getChildren().add(vbox);
                scrollPaneChat.setVvalue(1.0);
            }
        });
    }

    public void tryToAuth(ActionEvent actionEvent) {
        if (loginField.getText().equals("") || passwordField.getText().equals("")) {
            printLeftOrRight("Поля не могут быть пустыми");
        } else {
            if (socket == null || socket.isClosed()) {   // при неверном вводе пароля и попытке зайти второй раз  не сработает  условие, если не закрыть сокет
                // сокет закрываем в стр. 103 этого же класса
                connect();


                try {
                    out.writeUTF("/auth " + loginField.getText() + " " + passwordField.getText());
                    loginField.clear();
                    passwordField.clear();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void tryToRegister(ActionEvent actionEvent) {
        String login = regLoginField.getText();
        String password = regPasswordField.getText();
        String nick = regNickField.getText();

        if (login.equals("") || password.equals("") || nick.equals(""))
            printLeftOrRight("Поля не должны быть пустыми.");
        else {
            if (socket == null || socket.isClosed()) {   // при неверном вводе пароля и попытке зайти второй раз  не сработает  условие, если не закрыть сокет
                // сокет закрываем в стр. 103 этого же класса
                connect();
                try {
                    out.writeUTF("/reg " + login + " " + password + " " + nick);
                    regLoginField.clear();
                    regPasswordField.clear();
                    regNickField.clear();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void registerFormVisible() {
        if (regFormVisible) {
            upperPanel.setVisible(false);
            upperPanel.setManaged(false);
            registerPanel.setVisible(true);
            registerPanel.setManaged(true);
        } else {
            upperPanel.setVisible(true);
            upperPanel.setManaged(true);
            registerPanel.setVisible(false);
            registerPanel.setManaged(false);
        }

    }

    public void tryToChangeNick(ActionEvent actionEvent) {

        String newNick = changeNickField.getText();

        if (newNick.equals("")) printLeftOrRight("Поле не должно быть пустыми.");
        else {
            try {
                out.writeUTF("/chnick " + newNick);
                changeNickField.clear();
                changeNickFormVisible(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void changeNickFormVisible(boolean b) {
        if (b) {
            changeNickPanel.setVisible(true);
            changeNickPanel.setManaged(true);
        } else {
            changeNickPanel.setVisible(false);
            changeNickPanel.setManaged(false);
        }
    }

    public void changeNickFormShow(ActionEvent actionEvent) {
        changeNickFormVisible(true);
    }

}