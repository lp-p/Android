package com.example.roombasic2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button buttonInsert, buttonUpdate, buttonDelete, buttonClear;
    WordViewModel wordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);

        textView = findViewById(R.id.textView);
        wordViewModel.getAllWordsLive().observe(this, words -> {
            StringBuilder text = new StringBuilder();
            for (int i = 0; i < words.size(); i++) {
                Word word = words.get(i);
                text.append(word.getId()).append(":").append(word.getWord()).append("=").append(word.getChineseMeaning()).append("\n");
            }
            textView.setText(text.toString());
        });

        buttonInsert = findViewById(R.id.buttonInsert);
        buttonClear = findViewById(R.id.buttonClear);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonUpdate = findViewById(R.id.buttonUpdate);

        buttonInsert.setOnClickListener(v -> {
            Word word1 = new Word("Hello", "你好！");
            Word word2 = new Word("World", "世界！");
            wordViewModel.insertWords(word1, word2);
        });

        buttonClear.setOnClickListener(v -> wordViewModel.deleteAllWords());

        buttonUpdate.setOnClickListener(v -> {
            Word word = new Word("Hi", "你好啊!");
            word.setId(3);
            wordViewModel.updateWords(word);
        });

        buttonDelete.setOnClickListener(v -> {
            Word word = new Word("Hi", "你好啊!");
            word.setId(3);
            wordViewModel.deleteWords(word);
        });
    }
}
