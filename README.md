# Reversi
androidのappとしてリバーシを開発してみるリポジトリ

コードについても備忘録として残していく

## 環境
Android Studio（以下AS）
ASで使用しているemu: Nexus 5X API 23

## Androidアプリ開発の基本
Androidのアプリ開発では，javaクラスとxmlファイルを記述します．
javaクラスは処理を、xmlファイルは画面構成を記述します．
このjavaクラスのことを「アクティビティ」といい，Activityクラスを
継承してつくられます．一方で，xmlファイルは画面のレイアウト
に関することを記述し「レイアウトファイル」と呼ばれます．

## MainActivity
javaのmainメソッドのようなもの．
Androidアプリケーションはあらかじめ指定した一つのアクティビティが最初に作成される
ことでアプリケーションが起動します．

<img src="https://github.com/smjro/Reversi/blob/master/fig/life_cycle.jpg" width="400px">

上図はアクティビティのライフサイクルと呼ばれ，アクティビティの状態遷移を表しています．
以上のようにアクティビティは開始された後で表示されたり，他のアクティビティに隠れたりと
状態の変化を繰り返ります．あるメソッドが呼ばれるタイミングで何かを実行したい場合には，
アクティビティメソッドの中でメソッドをオーバーライドすることで実現します．

-- 参考 --

[http://www.javadrive.jp/android/activity/index2.html](http://www.javadrive.jp/android/activity/index2.html)

## 設定画面の表示方法
下図の右上に表示されている様なandroidの設定画面の表示方法について
説明します．

<img src="https://github.com/smjro/Reversi/blob/master/fig/option.png" width="400px">

### onCreateOptionsMenu（MainActivity.java）
画面にオプションメニューを配置します．

```
getMenuInflater().inflate(R.menu.<FILE_NAME>, menu)
```

`/res/menu/<FILE_NAME>.xml`ここにオプションメニューの構成について記述します．

### onOptionsItemSelected（MainActivity.java）
onCreateOptionsMenuで設定したオプションを押した時の挙動を設定できます。

<img src="https://github.com/smjro/Reversi/blob/master/fig/option2.png" width="400px">

上図はオプションボタンを押した時の表示で，onCreateOptionsMenuで設定した
項目が表示されています．さらに各項目を選択したときの挙動をここで設定します．

```java
@Override
public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        case R.id.menuPref:
            openPref();
            break;
        default:
            break;
    }
    return super.onOptionsItemSelected(item);
}
```

項目毎の処理はswhich文で記述します．

-- 参考 --

[メニュー画面の作成](http://androidguide.nomaki.jp/html/menu/menuMain.html)

## Activityの切替
### AndroidManifest
Activityの切り替わりを可能とするための設定．ここでは，設定画面切替時の登録を行います．

別Activityを用意したときは
```
<activity
</activity>
```
を追加する必要があります．また，
```
<intent-filter>
</intent-filter>
```
が入ったactivityが優先して表示されます．

Androidには，アプリのデータを保存する機能の１つとして，
preference（プレファレンス）という機能があります．

下図は，そのpreferenceを使用して設定画面を作成したものです．

<img src="https://github.com/smjro/Reversi/blob/master/fig/activity.png" width="400px">

-- 参考　--

[preferenceを使って設定画面を作成してみる - Androidアプリ開発入門 -ANDROID ROID-Androidアプリ開発入門 -ANDROID ROID-](http://androidroid.info/android/preference/83/)

## 描画方法
背景に画像を表示したり，描画する方法を説明します．

このアプリでは`ReversiView`というViewを継承したクラスを作成し、
onDrawメソッドをオーバーライドし、そこに描画に関することを
記述しています．

onDrawメソッドは，システムがViewを描画する度に呼ばれるメソッドで，
引数のCanvasに描画した内容がViewとして表示されます．

さて、本題に戻りますが主に描画には，
- 自分で用意した画像を表示する
- 自分で描画する

の２種類があります．

### 自分で用意した画像を表示する
自分で用意した画像を直接表示させたい場合には`Bitmap`というものを使います．

まず，

```java
Bitmap screen = BitmapFactory.decodeResource(getResources(), R.drawable.<FIG_NAME>); // 画像の読み込み
```

でresのdrawbleにある画像を読み込みます．次に，

```
mBitmapScreen = Bitmap.createScaledBitmap(screen, mwidth, mheight, true); // 幅の指定
```

リサイズを行います．携帯によって画像を表示できるサイズが
変わってしまうので，次のようにしてviewサイズを調べます．

```java
// リソースからbitmapを作成
DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();

// view範囲の幅、高さを調べる
this.mwidth = dm.widthPixels;
this.mheight = dm.heightPixels;
```

view範囲については[ここ](http://qiita.com/a_nishimura/items/f557138b2d67b9e1877c)を参照してください．

最後に，以下で出力を行います．

```java
canvas.drawBitmap(mBitmapScreen, 0, 0, paint);
```

引数は左から，Bitmapのオブジェクト名，left，top，Paintのオブジェクト名です．
最後の引数については`null`でも動くようで，詳しくは理解していません．

### 自分で描画する
自分で描画する際は，`Canvas`というものを使用します．主に，
- 矩形
- 円
- 楕円
- 角丸矩形
- 弧
- 点
- 線
- 文字列

があります．厳密には先ほどのBimapも仲間に入ります．
それぞれの使い方は以下を参照ください．

-- 参考 --
[AndroidのCanvasを使いこなす！ - 基本的な描画](https://tech.recruit-mp.co.jp/mobile/remember_canvas1/)

## XML
renディレクトリ下の各xmlファイルおよびAndroidManifest.xmlの説明を行います．

### layout
---
画面のレイアウトを設定します．

#### main.xml
```
match_parent // 最大幅で表示
```

-- 参考記事 --

[http://techacademy.jp/magazine/4448](http://techacademy.jp/magazine/4448)

### menu
---
#### mainmenu.xml
メニューのタイトルや表示順序，IDを設定します．

```
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">

    <item android:title="@string/menu_pref"
        android:id="@+id/menuPref"
        android:orderInCategory="0"
        android:icon="@android:drawable/ic_menu_info_details"></item>

    <item android:title="@string/menu_init"
        android:id="@+id/menuInit"
        android:orderInCategory="1"
        android:icon="@android:drawable/ic_menu_revert"></item>

    <item android:title="@string/menu_exit"
        android:id="@+id/menuExit"
        android:orderInCategory="2"
        android:icon="@android:drawable/ic_menu_close_clear_cancel"></item>
</menu>
```
android:orderInCategoryの数字の大きさで表示順序を決定します．

### values
---
- colors.xml
- dimens.xml
- strings.xml
- styles.xml

#### strings
mainmenu.xmlの設定で`android:title="@string/menu_exit"`とありましたが，
@string/下はこのstrings.xmlで設定した文字列を参照しています．

### openPref
設定を行うActivityを起動します．

Activityから他のActivityを呼び出すためには，Intentを作成し`startActivity()`
にそれを渡します．

-- 参考記事 --

[http://qiita.com/ymotongpoo/items/d8a054f6fc93d069cb37](http://qiita.com/ymotongpoo/items/d8a054f6fc93d069cb37)

## ReversiView
オセロの表示処理を行います。

グラフィクス描画に使用するクラスは、一般的に「View」クラスのサブクラスとして作成します。Viewとは画面に表示される部品（androidでいうウィジェット的なもの？）の基本となるクラスです。この組み込みはActivityクラスで「setContentView」を使って行います。

雛形として次の様な形で定義されることが多いようです。今回も以下の形にならっています。

``` java
class クラス extends View {
  public コンストラクタ(Context context) {
    super(context);
    ……初期化処理……
  }
  
  public void onDraw(Canvas c){
    ……描画の処理……
  }
}
```

## Cell
リバーシ上のセルを設定

### RectFクラス
矩形を表す
左上の座標（x1, y1）と右下の座標（x2, y2）を設定する
```
new RectF(x1, y1, x2, y2)
```

## Board
リバーシの盤面上の設定

## 参考サイト
参考にさせて頂いたサイト

[http://nobuo-create.net/category/java-beginner/](http://nobuo-create.net/category/java-beginner/)
