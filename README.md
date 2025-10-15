# Clog 📝🚀  
**AI-powered commit history summarizer for Android**  

Clog is an Android app built with **Kotlin + Jetpack Compose** that fetches your **GitHub commit history** and generates **AI-powered weekly summaries** using the **Google Gemini API**.  
It automates a previously manual reporting task, saving developers time and making project updates easy to share.  

---

## ✨ Features  
- 🔄 **Fetch GitHub commit history** with a single tap  
- 🤖 **AI-generated weekly summaries** powered by Gemini  
- 💾 **Offline caching** with RoomDB for near-instant summary access (200ms cached reads)  
- 📤 **Export & share as PDF** for reports, changelogs, or team updates  
- ⚡ **Modern Android stack**: MVVM, StateFlow, Hilt, Retrofit, Coroutines  

---

## 📸 Screenshots  


---

## 🎥 Demo  
![Clog Demo]()  


## 🛠️ Tech Stack  
- **Kotlin + Jetpack Compose** – Modern Android UI  
- **MVVM + StateFlow** – Reactive architecture  
- **RoomDB** – Offline caching  
- **Retrofit + Coroutines + Hilt** – Network + DI  
- **Firebase** – Auth & backend services  
- **Google Gemini API** – AI commit summarization  

---

## 🚀 Getting Started  

### Prerequisites  
- Android Studio Ladybug or newer  
- A GitHub personal access token  
- A Gemini API key (from Google AI Studio)  
- Firebase project setup (optional, for auth/storage)  

### Setup  
1. Clone the repo:  
   ```bash
   git clone https://github.com/your-username/clog.git
   cd clog

2. Add Firebase configuration:
   ```bash
   Place google-services.json in the app/ folder

3. Sync and run in Android Studio
