Start Appium server either by launching Appium Desktop app (preferred) or by executing

```bash
$ appium .
```

List the available android simulators (which are installed on your machine)

```bash
$ emulator -list-avds
```

Launch an android simulator device for appium to connect to

```bash
$ emulator @avd
```

For example, if you named your device Pixel_API_29, replace avd above with Pixel_API_29.
If you encounter an error launching the emulator, make sure to add ${ANDROID_SDK}/emulator to your PATH in your bash_profile file so that the correct emulator binary is prioritized.
Make sure you get the following path:
```bash
$ which emulator           
$ /Users/frozenbe/Library/Android/sdk/emulator/emulator
```

Go back to current repository (rac-mobile-tests), and follow the steps below:

```bash
# Change into the appium directory
$ cd appium
# Launch all maven tests
$ mvn verify
```

# Positive path test run
![Positive path](https://github.com/frozenbe/factorialTester/raw/master/factorial%20positive%20path%20test.gif)


# Negative path test run
![Negative path](https://github.com/frozenbe/factorialTester/raw/master/factorial%20negative%20path%20test.gif)

