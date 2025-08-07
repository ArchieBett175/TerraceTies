
# Terrace Ties ⚽

> **A Self-contained Android App connecting lower league football fans and bringing communities closer together**

[![First Class Grade](https://img.shields.io/badge/Grade-First%20Class-gold)](https://github.com/yourusername/terrace-ties)
[![Android](https://img.shields.io/badge/Platform-Android-green)](https://developer.android.com/)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-purple)](https://kotlinlang.org/)
[![Firebase](https://img.shields.io/badge/Backend-Firebase-orange)](https://firebase.google.com/)

## 🏆 Project Achievement
**2024 - First Class Grade Received** for this comprehensive mobile application project.

## 📱 About Terrace Ties

Terrace Ties is an Android application designed to strengthen community engagement around non-league football in the UK. The app focuses on bringing fans of lower league teams closer together through communication, information sharing, and local team support.

### 🎯 Problem Statement
Non-league football clubs are deeply rooted in local communities but lack the massive broadcasting deals and merchandise sales of Premier League teams. While the Premier League generates around £3.3 billion annually from TV rights, the Championship brings in only £179 million, with even steeper drop-offs further down the pyramid. These smaller clubs rely heavily on matchday income and community support to survive.

### 💡 Solution
Terrace Ties aims to bridge this gap by creating a platform that:
- Strengthens community engagement with local teams
- Builds connections between fans
- Shines a light on overlooked lower-league games
- Supports communication and information sharing
- Encourages support for local fixtures

## 🚀 Features

### Current Implementation
- **User Authentication** - Secure login and registration system
- **User Profiles** - Personalized fan profiles
- **Team Lists** - Browse and follow local football teams
- **Match Details** - View upcoming and past match information
- **Settings Panel** - Customize app preferences
- **Messaging System** - Chat functionality for fan communication
- **Main Feed** - Stay updated with local football news and updates

### Planned Features
- **Real-time Chat** - Enhanced messaging with Firebase Cloud Messaging
- **Push Notifications** - Live match updates and team news
- **iOS Version** - React Native or Swift implementation
- **App Store Release** - Google Play Store and Apple App Store deployment

## 🛠️ Tech Stack

### Mobile Development
- **Android Studio** - Primary development environment
- **Kotlin** - Programming language
- **Android SDK** - Native Android development

### Backend & Cloud Services
- **Firebase** - Cloud database and authentication
  - Authentication system for user profiles
  - Cloud Firestore for data storage
  - Firebase Cloud Messaging for notifications
- **REST APIs** - Football data integration

### Design & UI/UX
- **Figma** - UI/UX design and prototyping
- **Material Design** - Android design guidelines
- **Custom Styling** - Tailored football-themed interface

## 🎨 Design Evolution

The application has undergone significant design improvements:

### Version 1.0 - Initial Implementation
- Basic functionality with minimal styling
- Core features working but limited visual appeal
- Focus on backend implementation and data flow

### Version 2.0 - Redesigned UI (Figma)
- Modern, user-friendly interface
- Consistent color scheme throughout
- Enhanced user experience with improved navigation
- Football-themed design elements
- [View Figma Design](your-figma-link-here)

## 📁 Project Structure

```
terrace-ties/
├── app/
│   ├── src/main/java/
│   │   ├── activities/          # Main app activities
│   │   ├── fragments/           # UI fragments
│   │   ├── adapters/           # RecyclerView adapters
│   │   ├── models/             # Data models
│   │   ├── utils/              # Utility classes
│   │   └── firebase/           # Firebase integration
│   ├── src/main/res/
│   │   ├── layout/             # XML layout files
│   │   ├── drawable/           # Images and icons
│   │   ├── values/             # Colors, strings, styles
│   │   └── menu/               # Menu resources
│   └── build.gradle            # App-level dependencies
├── gradle/                     # Gradle wrapper files
└── build.gradle               # Project-level build file
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox (or later)
- Android SDK API level 21+
- Firebase account
- Git

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/terrace-ties.git
   cd terrace-ties
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned repository

3. **Firebase Setup**
   - Create a new Firebase project at [Firebase Console](https://console.firebase.google.com/)
   - Add your Android app to the Firebase project
   - Download `google-services.json` and place it in the `app/` directory
   - Enable Authentication and Firestore Database

4. **Build and Run**
   ```bash
   # Connect an Android device or start an emulator
   # Click "Run" in Android Studio or use:
   ./gradlew assembleDebug
   ```

### Firebase Configuration

1. **Authentication**
   - Enable Email/Password authentication in Firebase Console
   - Configure sign-in methods as needed

2. **Firestore Database**
   ```javascript
   // Example collection structure
   users/
   ├── {userId}/
   │   ├── name: string
   │   ├── email: string
   │   ├── favoriteTeam: string
   │   └── profileImage: string
   
   teams/
   ├── {teamId}/
   │   ├── name: string
   │   ├── league: string
   │   ├── location: string
   │   └── followers: array
   
   matches/
   ├── {matchId}/
   │   ├── homeTeam: string
   │   ├── awayTeam: string
   │   ├── date: timestamp
   │   └── venue: string
   ```

## 🏗️ Development Challenges & Solutions

### Firebase Integration
**Challenge**: Being new to cloud computing and cloud databases made Firebase implementation challenging.

**Solution**: 
- Completed extensive research on Firebase services
- Implemented step-by-step integration for:
  - Profile storage and authentication
  - Notification system for API updates
  - Chat message sending and receiving
  - Real-time database updates

**Current Status**: Basic implementation complete, with ongoing improvements for chat messaging system.

### Future Learning Goals
- Advanced Firebase features (Cloud Functions, Analytics)
- AWS cloud services integration
- CI/CD pipeline implementation
- Advanced mobile development patterns

## 🛣️ Roadmap

### Phase 1: Current (Completed)
- ✅ Basic Android app structure
- ✅ User authentication system
- ✅ Core UI implementation
- ✅ Firebase basic integration
- ✅ Team and match data display

### Phase 2: Enhanced Features (In Progress)
- 🔄 Improved UI implementation from Figma designs
- 🔄 Enhanced chat messaging system
- 🔄 Push notification system
- 🔄 Real-time data updates

### Phase 3: Platform Expansion (Planned)
- 📋 iOS version development (React Native/Swift)
- 📋 Cross-platform feature parity
- 📋 App Store submission preparation
- 📋 Beta testing program

### Phase 4: Release & Scale (Future)
- 📋 Google Play Store release
- 📋 Apple App Store release
- 📋 User feedback integration
- 📋 Community building features
- 📋 Partnership with local clubs

## 🧪 Testing

```bash
# Run unit tests
./gradlew test

# Run instrumentation tests
./gradlew connectedAndroidTest

# Generate test coverage report
./gradlew jacocoTestReport
```

## 📊 Performance Considerations

- **Database Optimization**: Efficient Firestore queries with proper indexing
- **Image Handling**: Optimized image loading and caching
- **Memory Management**: Proper lifecycle management for activities and fragments
- **Network Efficiency**: Minimal API calls with smart caching strategies

## 🤝 Contributing

While this is primarily an academic project, contributions and feedback are welcome:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👤 Author

**Archie Bett**
- *Creative Problem-solver with a passion for innovation*
- Email: [archiebett@hotmail.co.uk](mailto:archiebett@hotmail.co.uk)
- LinkedIn: [Archie Bett](https://www.linkedin.com/in/archie-bett-/)
- Portfolio: [My Site](https://archiebettportfolio.vercel.app/)

## 🙏 Acknowledgments

- **Firebase Team** - For excellent documentation and cloud services
- **Android Development Community** - For countless tutorials and support
- **Local Football Clubs** - For inspiring this community-focused project
- **Academic Supervisors** - For guidance throughout the development process

## 📈 Project Impact

This project demonstrates:
- **Technical Skills**: Mobile development, cloud computing, database design
- **Problem-Solving**: Identifying and addressing real community needs
- **Project Management**: Complete development lifecycle management
- **UI/UX Design**: User-centered design principles and iteration
- **Research Skills**: Market analysis and technology evaluation

---

**Note**: This application is part of an academic project that received a First Class grade. The focus was on demonstrating technical competency, problem-solving skills, and real-world application development.
