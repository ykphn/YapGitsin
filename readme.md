# YapGitsin - Mobil Uygulama

YapGitsin, kullanıcıların çeşitli yemek tariflerini keşfetmelerini ve favori tariflerini kaydetmelerini sağlayan modern bir mobil uygulamadır. Android için geliştirilmiştir ve **Light / Dark** tema desteği ile **Türkçe ve İngilizce dil desteği** sunar.

## Özellikler / Features
- **Kullanıcı kimlik doğrulama**: Supabase Auth kullanılarak güvenli giriş ve kayıt sistemi  
- **Şifre sıfırlama**: Supabase üzerinden e-posta ile şifre sıfırlama desteği  
- **Tarif listesi ve filtreleme**: Meals API kullanılarak tariflerin çekilmesi ve kategori / malzeme bazlı filtreleme  
- **Favorilere ekleme ve yönetim**: Kullanıcının favori tariflerini Supabase Storage veya veritabanında saklama  
- **Profil yönetimi**: Kullanıcı bilgilerini görüntüleme ve güncelleme  
- **Modern UI**: Jetpack Compose ile responsive ve reaktif kullanıcı arayüzü  

## Ekran Görüntüleri

### Light Mod

<table>
  <tr>
    <td><img src="screenshots/light/login.png" width="180"/><br>Giriş</td>
    <td><img src="screenshots/light/register.png" width="180"/><br>Kayıt</td>
    <td><img src="screenshots/light/forgot.png" width="180"/><br>Şifre Sıfırlama</td>
  </tr>
  <tr>
    <td><img src="screenshots/light/profile.png" width="180"/><br>Profil</td>
    <td><img src="screenshots/light/meals.png" width="180"/><br>Yemekler</td>
    <td><img src="screenshots/light/filter.png" width="180"/><br>Filtreleme</td>
    <td><img src="screenshots/light/details.png" width="180"/><br>Detay</td>
  </tr>
</table>

### Dark Mod

<table>
  <tr>
    <td><img src="screenshots/dark/login.png" width="180"/><br>Giriş</td>
    <td><img src="screenshots/dark/register.png" width="180"/><br>Kayıt</td>
    <td><img src="screenshots/dark/forgot.png" width="180"/><br>Şifre Sıfırlama</td>
  </tr>
  <tr>
    <td><img src="screenshots/dark/profile.png" width="180"/><br>Profil</td>
    <td><img src="screenshots/dark/meals.png" width="180"/><br>Yemekler</td>
    <td><img src="screenshots/dark/filter.png" width="180"/><br>Filtreleme</td>
    <td><img src="screenshots/dark/details.png" width="180"/><br>Detay</td>
  </tr>
</table>



## Teknolojiler / Technologies
- **Kotlin**  
- **Jetpack Compose**  
- **MVVM Mimarisi**  
- **Hilt** (Dependency Injection)  
- **Jetpack Navigation** (Compose için ekranlar arası geçiş)  
- **Supabase** (Backend ve veri yönetimi) 
