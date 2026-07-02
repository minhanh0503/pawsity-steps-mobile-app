# EECS4443 Group Project: Team 7 - Pawsitive Steps

## Overview

**Pawsitive Steps** is a gamified mobile fitness and wellness tracking Android application developed as part of the EECS4443 course project. The application encourages users to build healthy habits by tracking daily step counts and rewarding progress through a virtual pet system.

As users complete fitness goals, they unlock in-app rewards such as items, progression, and pet interactions, making habit formation more engaging and motivating.

This project also serves as a research prototype to evaluate how **navigation design and gamification influence usability, performance, and user satisfaction** in mobile wellness applications.

---

## Architecture

The application follows the **Model-View-Controller (MVC)** architectural pattern and adopts a **Single-Activity Architecture**, consistent with modern Android development practices. This approach improves modularity, simplifies navigation handling, and enhances maintainability.

---

## UI Overview 
<img width="406" height="452" alt="image" src="https://github.com/user-attachments/assets/cb4e73b6-5871-467e-a2ad-56a744db8530" />
For a detailed and full version of our UI design, visit our Figma: https://www.figma.com/design/HmOfR3wYs97AWpwDWJrf6K/Team7-Project4443-Wireframe?node-id=0-1&p=f&t=aYRvlHo0KvOcicRE-0 

---

## Demo

Watch the full app walkthrough here:

- App Demo Video: [https://your-video-link-here](https://youtu.be/2to3n-sVJxM)

The demo showcases core gameplay, navigation flows, and the gamification system (step tracking, quests, shop, and pet progression).

---

## Research Study Summary

This project includes an embedded user study investigating the impact of different navigation structures on user experience and performance.

### Navigation Conditions Tested
- **A:** Bottom Navigation Bar  
- **B:** Hamburger Side Menu  
- **C:** Hybrid Navigation (Bottom Bar + Hamburger Menu)

### Study Design
- 12 participants in total
- Within-subject and between-subject design
- Counterbalanced trial orders (ABC, ACB, BAC, etc.)
- Tasks completed across 3 UI versions per participant (phone group)

### Core Tasks
Participants completed the following workflow:
1. Navigate Home → Quest screen and claim a card  
2. Quest → Shop and purchase a hat  
3. Shop → Closet and equip the hat  
4. Closet → Settings (end of trial)

### Measured Metrics
- Task completion time
- Error count
- CPU usage
- Memory (RSS) usage
- Post-study usability survey

---

## Key Findings (Summary)

- No statistically significant differences were found between navigation styles in:
  - Task completion time
  - Error rates
  - CPU usage
  - Memory usage

- **Hybrid navigation (C)**:
  - Fastest average task completion time
  - Highest user preference in surveys

- **Hamburger navigation (B)**:
  - Highest CPU usage on average

- User feedback indicated:
  - High usability and satisfaction overall
  - Strong preference for hybrid navigation
  - No noticeable lag or performance issues

Overall, results suggest that **navigation design has a stronger impact on user preference than measurable performance differences** in short-term usage scenarios.

---

## Architecture

This project was implemented using:
- **MVC (Model-View-Controller)** design pattern
- **Single Activity Architecture**

These patterns were chosen to support scalability, modularity, and clean separation of concerns in Android development.

---

## Running and Testing

### Requirements
- Android Studio (recommended latest version)
- Android Emulator: **Medium Phone API 36.1 (API 36)**

### Notes
- Developed and tested using API 36 to match experimental device conditions.
- UI testing was performed using **Maestro Studio**.

### UI Testing
- Maestro Studio was used for automated UI testing workflows: https://www.youtube.com/watch?v=mGHJj6b67NQ  
- Participant trial demo: https://www.youtube.com/shorts/fExQJlITuM4

### Unit Testing
Unit tests were implemented to validate core logic and ensure expected behavior throughout development.

---

## Group Members

- Kevin Ho — 217471129  
- Minh Anh Nguyen — 219116714  
- An Vu — 218207043  

---

## Credits

- BGM: ねこあつめのテーマ · Ryo Shintani  
- UI testing framework: Maestro Studio  


