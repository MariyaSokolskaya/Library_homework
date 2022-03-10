package com.example.library;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.HashMap;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    ListView bookList;
    OpenHelper openHelper;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookList = findViewById(R.id.book_list);

        openHelper = new OpenHelper(this);
        database = openHelper.getWritableDatabase();

        //TODO подготовка данных
        LinkedList<Book> bookLinkedList = new LinkedList<>();
        bookLinkedList.add(new Book("Основание","А.Азимов",
                2015, R.drawable.osnovanie));
        bookLinkedList.add(new Book("Преступление и назазание",
                "Ф.Достоевский", 1972, R.drawable.prestuplenie));
        bookLinkedList.add(new Book("Шинель",
                "Н.Гоголь", 1998, R.drawable.shinel));
        bookLinkedList.add(new Book("Роковые яйца","М.Булгаков",
                2018, R.drawable.book));
        bookLinkedList.add(new Book("Колобок","народ",
                2001, R.drawable.book));

        //TODO создать массивы с ключами и идентификаторами
        String[] keyArray = {"title", "author", "year", "cover"};
        int [] idArray = {R.id.book_title, R.id.author, R.id.year, R.id.cover};

        //TODO создание списка map для адаптера
        LinkedList<HashMap<String, Object>> listForAdapter = new LinkedList<>();
        for (int i = 0; i < bookLinkedList.size(); i++) {
            HashMap<String, Object> bookMap = new HashMap<>();
            bookMap.put(keyArray[0], bookLinkedList.get(i).title);
            bookMap.put(keyArray[1], bookLinkedList.get(i).author);
            bookMap.put(keyArray[2], bookLinkedList.get(i).year);
            bookMap.put(keyArray[3], bookLinkedList.get(i).coverId);
            listForAdapter.add(bookMap);

            //TODO проверить на дубликаты
            ContentValues values = new ContentValues();
            values.put(OpenHelper.COLUMN_AUTHOR,
                    bookLinkedList.get(i).author);
            values.put(OpenHelper.COLUMN_TITLE,
                    bookLinkedList.get(i).title);
            values.put(OpenHelper.COLUMN_YEAR,
                    bookLinkedList.get(i).year);
            database.insert(OpenHelper.TABLE_NAME, null, values);
        }
        //TODO создание адаптера
//        ArrayAdapter<Book> adapter = new ArrayAdapter<>(this,
//                R.layout.list_item, bookLinkedList);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,
                listForAdapter, R.layout.list_item, keyArray, idArray);

        bookList.setAdapter(simpleAdapter);
        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int i, long l) {
                Toast.makeText(getApplicationContext(),
                        bookLinkedList.get(i).toString(),
                        Toast.LENGTH_SHORT).show();
                switch (view.getId()){
                    case R.id.author:
                }

                Cursor cursor = database.query(OpenHelper.TABLE_NAME,
                        new String[]{OpenHelper.COLUMN_AUTHOR,
                            OpenHelper.COLUMN_TITLE,
                            OpenHelper.COLUMN_YEAR},
                        null,
                        null,
                        null,
                        null,
                        OpenHelper.COLUMN_AUTHOR);
                cursor.moveToFirst();
                while (cursor.moveToNext()){
                    String author = cursor.getString(
                            cursor.getColumnIndex(OpenHelper.COLUMN_AUTHOR)
                    );
                    String title = cursor.getString(
                            cursor.getColumnIndex(OpenHelper.COLUMN_TITLE)
                    );
                    int year = cursor.getInt(
                            cursor.getColumnIndex(OpenHelper.COLUMN_YEAR)
                    );
                    //TODO показать результаты на интерфейсе
                }
                cursor.close();
            }
        });
        //TODO дома: создать интерфейс добавления/удаления книги
        simpleAdapter.notifyDataSetChanged();//обновление экрана

    }

    @Override
    protected void onStop() {
        super.onStop();
        database.close();
    }
}