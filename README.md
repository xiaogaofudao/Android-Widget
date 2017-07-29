
[![](https://jitpack.io/v/xiaogaofudao/Android-Widget.svg)](https://jitpack.io/#xiaogaofudao/Android-Widget)

# Android-Widget

Toast 一个关闭系统消息通知后依然可以显示的Toast
Dialog 自定义弹出框

具体使用如下：
Step 1. Add the JitPack repository to your build file


	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
Step 2. Add the dependency


  	dependencies {
		compile 'com.github.xiaogaofudao.Android-Widget:toast:1.0.3'
		compile 'com.github.xiaogaofudao.Android-Widget:dialog:1.0.3'
	}

Toast

使用方法和Toast完全一致，如下：

Toast.makeText(MainActivity.this,"我是一个屏蔽通知我也是可以显示的Toast",Toast.LENGTH_SHORT).show();


  
  最后需要注意的是，此Toast非彼Toast，应该使用“import com.gaogeek.toast.Toast”，建议在BaseActivity中如下使用：
  
  
	public void showShortText(String text) {
		Toast.makeText(mActivity, text, Toast.LENGTH_SHORT).show();
	}
	
	
Dialog

使用方法和Toast基本一致，如下：

	HintDialog.makeText(MainActivity.this,"我是一个消息弹出框").show();

	HintDialog.makeText(MainActivity.this, "我是一个有回调的消息弹出框，点击确定弹出toast").setOnConfirmListener(new OnConfirmListener() {
		@Override
		public void OnConfirm() {
			Toast.makeText(MainActivity.this,"确定", Toast.LENGTH_SHORT).show();
		}
	}).show();

  
  最后需要注意的是，记得 “import com.gaogeek.dialog.HintDialog”

