package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import org.json.simple.JSONObject;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
    }

    public void clickRegistration(View view) {
        // создаём экземляр класса для работы соответсвенно
        Requests requests = new Requests();
        // необязательно, но сохраним ответ отдельно, а так можно напрямую
        JSONObject result = new JSONObject();
        // пример приветсвия, метод ничего не возвращает. Просто передаёт привет в консоли
        requests.hello();

        // непосредственно метод входа. Он ничего не возвращает и запускается ФОНОВЫМ ПРОЦЕССОМ
        requests.login("sada@adsad.ru", "123456");
        // при вызове ожидает (либо ждёт 10 секунд) завершения потока
        // в данном случае ожидает завершения входа
        // возвращает json с которым уже можно рабоать
        result = requests.getResult();
        System.out.println("Login " + result);

        // ничего не возвращает и просто ждёт завершения потока
        requests.waiting();
        // соответсвенно работа напрямую с ответом. Но он не ясный пока поток не завершится
        // без .waiting() не рекомендуется к применению
        System.out.println("winner login " + requests.result);

        System.out.println("Result find: " + result.get("result")); // если нет ключа будет null
        System.out.println("Keys: " + result.keySet().toString());

        // Пример вызова с неправильными данными
        requests.login("sada@adsad.ru", "123412312356");
        System.out.println("Login false " + requests.getResult());
        // если неверные данные вернёт {"result":false}
        System.out.println("Result find: " + requests.getResult().get("result"));
        System.out.println("Keys: " + requests.getResult().keySet().toString());

        /*
        * теперь о минусах
        * если что то затупило, а такое может быть и не удалось запрос серверу осуществить
        * стоит запрос повторить тогда пройдет. Это я позже сделаю
        *
        * отсюда может быть FatalError
        *
        * соответсвенно result может быть вовсе без ключей, значит запрос скорее всего не совершился
        * или вовсе не был создан, вызван и т.д.
        * */
        Intent intent = new Intent(MainActivity.this, FirstRegActivity.class);
        startActivity(intent);
    }

    public void clickInput(View view) {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}