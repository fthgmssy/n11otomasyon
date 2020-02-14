# N11 Test Otomasyon

## Projenin import edilmesi

Proje local bilgisayara kopyalandıktan sonra Eclipse veya IntelliJ IDEA üzerinde
"File -> Import -> Maven -> Existing Maven Projects" seçeneğiyle ilerlenerek Projenin ana dizini gösterilir.
Proje import edildikten sonra "src/test/java/com/framework/test" altındaki "Kampanyalar" class'ına gidilir.
Class içindeki "kampanyalar_KategoriBazli" metodu sağ tıklanıp Run As menüsünde "TestNG Test" seçilir.

## Selenium Grid ve Node'ların Docker üzerinde ayağa kaldırılması ve izlenilmesi

Sırasıyla aşağıdaki komutlar çalıştırılır.

1 - docker network create grid

2 - docker run -d -p 4444:4444 --net grid --name selenium-hub selenium/hub:latest

3 - docker run -d -p 5900:5900 --net grid -e HUB_HOST=selenium-hub -v /dev/shm:/dev/shm selenium/node-chrome-debug:latest

Test sırasında tarayıcı üzerinden koşum izlenilmek isteniyorsa VNCViewer uygulaması üzerinden aşağıdaki bilgiler ile izleme sağlanabilir.

##### Host: localhost:5900
##### Password: secret


## Testlerin Local'de veya Docker üzerinde koşulması
Proje dizininde "\src\main\Resources\default.properties" dosyasında "isRemote" parametresi "false" olarak değiştirilirse testler local bilgisayar üzerinde koşulacaktır.


## Çıktı dosyasının kontrol edilmesi
Test sonunda ilgili Excel çıktı dosyası proje ana dizininde output.xlsx adında oluşacaktır.


## Gereksinimler

- Eclipse veya IntelliJ IDEA
- IDE üzerinde Maven ve TestNG eklentilerinin kurulu olması
- Docker
