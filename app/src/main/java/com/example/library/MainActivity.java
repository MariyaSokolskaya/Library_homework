package com.example.library;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    ListView bookList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookList = findViewById(R.id.book_list);
        //TODO подготовка данных
        LinkedList<Book> bookLinkedList = new LinkedList<>();
        bookLinkedList.add(new Book("Гарри Поттер","Роулинг"));
        bookLinkedList.add(new Book("Идиот","Ф.Достоевский"));
        bookLinkedList.add(new Book("Гиперболоид инженера Гарина",
                "А.Толстой"));
        bookLinkedList.add(new Book("Роковые яйца","М.Булгаков"));
        bookLinkedList.add(new Book("Колобок","народ"));
        //TODO создание адаптера
        ArrayAdapter<Book> adapter = new ArrayAdapter<>(this,
                R.layout.list_item, bookLinkedList);

        bookList.setAdapter(adapter);
        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int i, long l) {
                Toast.makeText(getApplicationContext(),
                        bookLinkedList.get(i).toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        //TODO дома: создать интерфейс добавления/удаления книги
        adapter.notifyDataSetChanged();//обновление экрана
    }
}