package com.gui.components;

import com.GuiRunner;
import com.models.ConnectionDetails;
import com.models.UserTO;
import com.rest_providers.UserProviderImpl;
import com.sound_utils.Microphone;
import com.sound_utils.Speaker;
import com.udp_communication.SingleClientVoiceSender;
import com.udp_communication.VoiceReceiver;
import com.udp_communication.VoiceReceiverImpl;
import com.udp_communication.VoiceSender;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

@Controller
public class CallPageController {
    @Getter
    @Setter
    private static boolean visible = false;
    private VoiceSender voiceSender;
    private VoiceReceiver voiceReceiver;
    private boolean sendingVoice = false;

    private UserTO selectedUser;

    @Autowired
    private UserProviderImpl userProvider;

    @FXML
    private TableView<UserTO> usersTable;

    @FXML
    private TableColumn<String, UserTO> usernameColumn;

    @FXML
    private TableColumn<Boolean, UserTO> favouriteColumn;

    @FXML
    private TableColumn<Boolean, UserTO> statusColumn;

    @FXML
    private Font x1;

    @FXML
    private Color x2;

    @FXML
    private Button disconnectBtn;

    @FXML
    private Button muteBtn;

    @FXML
    private Button callBtn;

    @FXML
    private Label selectedUserLbl;

    @FXML
    void initialize() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory("username"));
        statusColumn.setCellValueFactory(new PropertyValueFactory("active"));
        favouriteColumn.setCellValueFactory(new PropertyValueFactory("favourite"));
        initRefreshingUsersThread();
        muteBtn.setDisable(true);
    }

    @FXML
    void updateSelectedUser() {
        selectedUser = usersTable.getSelectionModel()
                                 .getSelectedItem();
        updateSelectedUserInfo();
    }

    @FXML
    void call(ActionEvent event) throws
                                 IOException,
                                 LineUnavailableException {
        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, true);
        ConnectionDetails receiverConnection = new ConnectionDetails(InetAddress.getByName("localhost"), 5555, 1024);
        ConnectionDetails senderConnection = new ConnectionDetails(InetAddress.getByName(selectedUser.getIPAddress()),
                                                                   5555,
                                                                   1024);
        Speaker speaker = new Speaker(format);
        Microphone microphone = new Microphone(format);

        voiceReceiver = new VoiceReceiverImpl(receiverConnection, speaker);
        voiceSender = new SingleClientVoiceSender(senderConnection, microphone);

        voiceReceiver.startListening();
        voiceSender.startSending();

        sendingVoice = true;
        muteBtn.setDisable(false);
    }

    @FXML
    void mute(ActionEvent event) throws
                                 IOException {
        if (sendingVoice) {
            voiceSender.pauseSending();
        } else {
            voiceSender.startSending();
        }

        sendingVoice = !sendingVoice;
        muteBtn.setDisable(sendingVoice);
    }

    @FXML
    void disconnect(ActionEvent event) {
        voiceSender.stopSending();
        voiceReceiver.stopListening();
    }

    private void initRefreshingUsersThread() {
        new Thread(() -> {
            while (GuiRunner.isRunning()) {
                try {
                    if (CallPageController.isVisible()) {
                        List<UserTO> allUsers = userProvider.getAllUsers();
                        usersTable.setItems(FXCollections.observableArrayList(allUsers));
                    }

                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void updateSelectedUserInfo() {
        selectedUserLbl.setText(selectedUser.getUsername());
    }
}
