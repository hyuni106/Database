package kr.co.tjeit.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

import kr.co.tjeit.database.util.StudentDBManager;

public class MainActivity extends BaseActivity {

    private android.widget.Button insertBtn;
    private android.widget.Button updateBtn;
    private android.widget.Button deleteBtn;
    private android.widget.Button queryBtn;
    private android.widget.EditText contentEdt;

    public StudentDBManager mDBManager;
    private EditText stdNumEdt;
    private EditText nameEdt;
    private EditText depertmentEdt;
    private EditText gradeEdt;
    private Button queryBtn01;
    private Button queryBtn02;
    private Button queryBtn03;
    private Button queryBtn04;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv = new ContentValues();
                cv.put("stdNum", stdNumEdt.getText().toString());
                cv.put("name", nameEdt.getText().toString());
                cv.put("department", depertmentEdt.getText().toString());
                cv.put("grade", Integer.parseInt(gradeEdt.getText().toString()));

                long insertedRowId = mDBManager.insert(cv);

                Toast.makeText(mContext, "학생 추가 : " + insertedRowId, Toast.LENGTH_SHORT).show();
                stdNumEdt.setText("");
                nameEdt.setText("");
                depertmentEdt.setText("");
                gradeEdt.setText("");
            }
        });

        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                필요한 데이터를 받아 출력
                String[] columns = {"id", "stdNum", "name", "department", "grade"};

                Cursor c = mDBManager.query(columns, null, null, null, null, null);

                if (c != null) {
//                    글자 초기화
                    contentEdt.setText("");
                    while (c.moveToNext()) {
                        int id = c.getInt(0);
                        String strNum = c.getString(1);
                        String name = c.getString(2);
                        String department = c.getString(3);
                        int grade = c.getInt(4);

//                        이어붙이기
                        contentEdt.append(String.format(Locale.KOREA, "%s : %d, %s, %s, %d\n==================================\n",
                                name, id, strNum, department, grade));
                    }
                }
            }
        });

//        queryBtn01.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                contentEdt.setText("");
//                Cursor c = mDBManager.getQuery00();
//                if (c != null) {
//                    while (c.moveToNext()) {
//                        String name = c.getString(0);
//                        contentEdt.append(name+"\n");
//                    }
//                }
//            }
//        });

        View.OnClickListener queryTest = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contentEdt.setText("");
                Cursor c = null;
                if (view.getId() == R.id.queryBtn01) {
                    c = mDBManager.getQuery01();
                    if (c != null) {
                        while (c.moveToNext()) {
                            String department = c.getString(0);
                            contentEdt.append(department+"\n");
                        }
                    }
                } else if (view.getId() == R.id.queryBtn02) {
                    c = mDBManager.getQuery02();
                    if (c != null) {
                        while (c.moveToNext()) {
                            String name = c.getString(0);
                            String department = c.getString(1);
                            contentEdt.append(name+" : " + department + "\n");
                        }
                    }
                } else if (view.getId() == R.id.queryBtn03) {
                    c = mDBManager.getQuery03();
                    if (c != null) {
                        while (c.moveToNext()) {
                            String name = c.getString(0);
                            int grade = c.getInt(1);
                            contentEdt.append(name+" : " + grade + "학년\n");
                        }
                    }
                } else if (view.getId() == R.id.queryBtn04) {
                    c = mDBManager.getQuery04();
                    if (c != null) {
                        while (c.moveToNext()) {
                            String stdNum = c.getString(0);
                            contentEdt.append(stdNum+"\n");
                        }
                    }
                }
            }
        };

        queryBtn01.setOnClickListener(queryTest);
        queryBtn02.setOnClickListener(queryTest);
        queryBtn03.setOnClickListener(queryTest);
        queryBtn04.setOnClickListener(queryTest);
    }

    @Override
    public void setValues() {
        mDBManager = StudentDBManager.getInstance(mContext);
    }

    @Override
    public void bindViews() {
        this.contentEdt = (EditText) findViewById(R.id.contentEdt);
        this.queryBtn04 = (Button) findViewById(R.id.queryBtn04);
        this.queryBtn03 = (Button) findViewById(R.id.queryBtn03);
        this.queryBtn02 = (Button) findViewById(R.id.queryBtn02);
        this.queryBtn01 = (Button) findViewById(R.id.queryBtn01);
        this.queryBtn = (Button) findViewById(R.id.queryBtn);
        this.deleteBtn = (Button) findViewById(R.id.deleteBtn);
        this.updateBtn = (Button) findViewById(R.id.updateBtn);
        this.insertBtn = (Button) findViewById(R.id.insertBtn);
        this.gradeEdt = (EditText) findViewById(R.id.gradeEdt);
        this.depertmentEdt = (EditText) findViewById(R.id.depertmentEdt);
        this.nameEdt = (EditText) findViewById(R.id.nameEdt);
        this.stdNumEdt = (EditText) findViewById(R.id.stdNumEdt);
    }
}
