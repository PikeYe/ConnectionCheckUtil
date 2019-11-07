# ConnectionCheckUtil
Check the network connection(a few DNS) with RxJava2

If you want to check the mobile network to see whether it is connected, you can try to call a few DNS with this tool. When calling, 
*  If one of the DNS is success, then the tool will callback at once, not need to wait for all the DNS success. 
*  Only when all the DNS are fail to connect, will the callback be called.

1.In the application, handle the extra exception in RxJava that when the Observer is dispose
```java
ConnectionCheckUtil.initRxJavaDisposeErrorHandler()
```

2.Use the tool like this way:
```java
ConnectionCheckUtil.connectionCheck(object : OnConnectionCheckListener {
            override fun onConnected() {
                //connected
            }
            override fun onDisconnected() {
                //not connected
            }
        })
```

[CSDN Blog Site](https://blog.csdn.net/u012166298/article/details/102958605)
