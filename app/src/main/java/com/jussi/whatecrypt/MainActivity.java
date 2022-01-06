package com.jussi.whatecrypt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText et_plainData;
    EditText et_eryptData;
    Button bt_Sha256;
    Button bt_des;
    Button bt_aes;
    Button bt_rsa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_plainData = findViewById(R.id.plainData);
        et_eryptData = findViewById(R.id.eryptData);
        bt_Sha256 = findViewById(R.id.sha256);
        bt_des = findViewById(R.id.des);
        bt_aes = findViewById(R.id.aes);
        bt_rsa = findViewById(R.id.rsa);

        bt_Sha256.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = et_plainData.getText().toString();
                et_eryptData.setText(HashUtils.getSha256(data));
            }
        });

        bt_des.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 密钥长度必须是 8 的倍数
                String key = "12345678";
                String content = et_plainData.getText().toString();
                byte[] result = DesUtils.encryptCbc(key.getBytes(StandardCharsets.UTF_8),
                        content.getBytes(StandardCharsets.UTF_8),
                        DesUtils.Padding.PKCS5_PADDING);
                et_eryptData.setText(new String(result, StandardCharsets.UTF_8));
            }
        });

        bt_aes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = et_plainData.getText().toString();
                try {
                    et_eryptData.setText(AesUtils.encryptAES(content));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        bt_rsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<Integer, String> keyMap = null;
                try {
                    keyMap = RSAUtils.genKeyPair();
                    String message = et_plainData.getText().toString();
                    String messageEn = RSAUtils.encrypt(message, keyMap.get(0));
                    et_eryptData.setText(messageEn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

}