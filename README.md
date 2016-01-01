IceSoap
=======

IceSoap provides quick, easy, asynchronous access to SOAP web services from Android devices. It allows for SOAP responses to be bound to Java POJOs via annotations (using a subset of xpath), while still retaining the speed of the native android XmlPullParser.

E.g. if I wanted to get data from a SOAP service that returned an envelope like this:

```
<Dictionary>
    <Id>1</Id>
    <Name>Blah</Name>
</Dictionary>
```

I'd write code like this to parse it:

```
@XMLObject("//Dictionary")
public class Dictionary {
    @XMLField("Id")
    private String id;

    @XMLField("Name")
    private String name;
}
```

IceSoap works more like Jackson than JAX-WS. You simply create a Java POJO to represent the object you want to get from the web service, annotate it with XPaths so it can find the fields it needs, then let IceSoap parse it for you. Features like the use of background threads and streamed parsing is baked into the code, so all you have to worry about is what to send and what to do with it when it comes back.

So [download IceSoap](https://github.com/AlexGilleran/IceSoap/wiki/Installation), read the [Getting Started Guide](https://github.com/AlexGilleran/IceSoap/wiki/Getting-Started-Contents), have a look at the [example](https://github.com/AlexGilleran/IceSoap/tree/master/IceSoapExample) and get going! Javadoc is [here](http://alexgilleran.github.io/IceSoap/javadoc/).

Github vs Google Code
---------------------
This used to be hosted on Google Code until Google did their thing and dumped it like an unwanted Christmas puppy. As a result the old issues that have been raised since the first release aren't actually on this repo, instead they're on the one that got converted here: https://github.com/AlexGilleran/icesoap-gcode-import/issues.
 
    Copyright 2012-2016 Alex Gilleran

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
