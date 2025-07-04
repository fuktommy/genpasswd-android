# genpasswd-android

## How to Build
1. Install android-sdk
```
sudo apt update
sudo apt install android-sdk openjdk-17-jdk
cp -a /usr/lib/android-sdk ~/
```
2. Get command line tools from https://developer.android.com/studio
3. Set up command line tools
```
unzip commandlinetools-linux-*_latest.zip
mkdir -p ~/android-sdk/cmdline-tools
mv ~/cmdline-tools ~/android-sdk/cmdline-tools/latest/
```
4. Set up env vars
```
export JAVA_HOME=$(readlink -f /usr/bin/javac | sed "s:/bin/javac::")
export ANDROID_HOME=${HOME}/android-sdk
PATH=$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/tools/bin:$ANDROID_HOME/platform-tools/bin:$JAVA_HOME/bin:$PATH
export ANDROID_KEYSTORE_PASS=xxxxxxxx
export ANDROID_KEY_PASS=xxxxxxxx
```
5. Build
```
cp /path/to/key app/my-release-key.jks
sdkmanager --licenses
sh gradlew :app:bundleRelease
find -name *.aab
```
