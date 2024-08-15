# Mobile Word Game

Bu proje, Android Studio ve Java kullanılarak geliştirilmiş bir mobil uygulamadır. Bu oyun, 8x10 ızgara üzerinde harflerin üstten aşağıya düştüğü bir kelime bulmacası oyununu içerir. Oyuncular, bu harflerden anlamlı kelimeler oluşturmaya çalışır. Dinamik bir oyun deneyimi sunan bu proje, çeşitli özellikler ve etkileşimler sağlar.

## Özellikler

- **Oyun Izgarası**: Üstten aşağıya doğru düşen harflerin yer aldığı 8x10 ızgara. Oyuncular, harfleri seçerek kelimeler oluşturabilir.
- **Dinamik Harf Düşüşü**: Harfler, oyunun başında her 5 saniyede bir düşer. Puan arttıkça, harflerin düşme süresi azalır ve en düşük süre 1 saniye olur.
- **Kelime Doğrulama**: Oyuncular, düşen harflerden kelimeler oluşturabilir. Oluşturulan kelimenin geçerli olup olmadığını kontrol edebilir veya seçilen harfleri kaldırabilir.
- **Özel Harfler**:
  - **Dinamit**: Düştüğü sütundaki tüm harfleri temizler.
  - **Cadı**: Düştüğü sütun hariç tüm sütunlara aynı harften birer tane düşürür.
- **Puan Sistemi**: Geçerli kelimeler oluşturulduğunda puan kazanılır. Yanlış kelimeler kaydedilir ve üç yanlış girişten sonra tüm sütunlara harf düşer.
- **Oyun Sonu ve Lider Tablosu**: Bir sütun tamamen dolduğunda oyun biter. Oyuncu puanları kaydedilir ve yüksekten düşüğe doğru sıralanmış olarak lider tablosunda gösterilir.

## Firebase Entegrasyonu

Bu projede Firebase kullanılmıştır ve özellikle Firebase Realtime Database, kelime listesi ve oyuncu puanlarının saklanması için kullanılmıştır. 

- **Kelime Listesi**: Oyun içinde kullanılacak kelimeler Firebase Realtime Database'de saklanır. Oyuncuların oluşturduğu kelimeler, bu veri tabanında saklanan kelime listesiyle karşılaştırılır.
- **Puanlar**: Oyuncu puanları Firebase Realtime Database'e kaydedilir ve lider tablosu bu puanlara göre oluşturulur.


## Başlarken

1. **Depoyu Kopyala**
   ```bash
   git clone https://github.com/kullaniciadi/mobile-word-game.git

2. **Projeyi Aç**

   Android Studio'da projeyi açın.

4. **Firebase Kurulumu**
   - Proje için Firebase'i yapılandırın.
   - `google-services.json` dosyasını projeye ekleyin.
  

5. **Derle ve Çalıştır**
   - Android Studio'da projeyi derleyin.
   - Uygulamayı bir Android cihazında veya emülatörde çalıştırın.

## Ekran Görüntüleri

<table style="border-spacing: 0; border-collapse: collapse; width: 100%;">
  <tr>
    <td style="padding: 0; vertical-align: middle; text-align: center;">
      <img src="https://github.com/user-attachments/assets/0a9e2a86-05b6-4943-a718-e866ffb3c07a" width="150" />
      <p style="text-align: center; font-size: 12px;">Özellik: Dinamit</p>
    </td>
    <td style="padding: 0; vertical-align: middle; text-align: center;">
      <img src="https://github.com/user-attachments/assets/4500cf7c-d683-4e22-9a95-724686b072ce" width="150" />
      <p style="text-align: center; font-size: 12px;">Dinamit Sonrası</p>
    </td>
    <td style="padding: 0; vertical-align: middle; text-align: center;">
      <img src="https://github.com/user-attachments/assets/6ca8f9e1-0722-48df-b7b9-9a96e5a72f3c" width="150" />
      <p style="text-align: center; font-size: 8px;">Özellik: Cadı</p>
    </td>
    <td style="padding: 0; vertical-align: middle; text-align: center;">
      <img src="https://github.com/user-attachments/assets/d714ddfb-365a-4ea4-8f69-af026de74a82" width="150" />
      <p style="text-align: center; font-size: 12px;">Cadı Sonrası</p>
    </td>
    <td style="padding: 0; vertical-align: middle; text-align: center;">
      <img src="https://github.com/user-attachments/assets/bb4b4650-7c2f-4f80-bc0c-71c269ddb57f" width="150" />
      <p style="text-align: center; font-size: 12px;">Game Over</p>
    </td>
    <td style="padding: 0; vertical-align: middle; text-align: center;">
      <img src="https://github.com/user-attachments/assets/6e5ae64b-fbcd-4e7d-a73b-7724044b852a" width="150" />
      <p style="text-align: center; font-size: 12px;">Liderler Tablosu</p>
    </td>
  </tr>
</table>


