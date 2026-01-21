Multi-Vendor Food Marketplace (Personal Project)
A scalable, multi-tenant e-commerce ecosystem designed for the food industry. This platform empowers multiple vendors to manage their own digital storefronts while providing a seamless, data-driven ordering experience for customers.

🚀 Key Innovation: Dynamic Pricing Engine
The core of this platform is a Rule-Based Dynamic Pricing Engine. It allows vendors to move beyond static pricing by:

* Vendor Autonomy: Each merchant can opt-in/out and configure their own triggers, duration, and price points.

🛠️ Technical Stack
Backend: Java 25, Spring Boot 4, Spring Security (JWT), Spring Data MongoDB, Spring Web.

Frontend: TypeScript, Next.js, Tailwind CSS, Shadcn UI.

Database: MongoDB.

Architecture: RESTful API, Role-Based Access Control (RBAC).

📊 Project Roadmap
Phase 1: Foundation (Completed ✅)
[✅] Database Schema Design for Users and Restaurants.

[✅] Secure Authentication System (JWT).

[✅] UI integration with Tailwind and Shadcn UI for Login and Register page in Merchant part website.

Phase 2: Core Features (In Progress 🏗️)

[ ] Completion of remaining UI integration with Tailwind and Shadcn UI for Merchant website. 

[ ] Creating UI for User (Customer) website

[ ] Dynamic Pricing Logic: Implementing the backend scheduler for automated price updates.

[ ] Merchant Dashboard: Interface for vendors to manage menus and view basic sales data.

[ ] Order Management: Real-time order flow from customer to vendor.

[ ] Role-Based Access Control.

[ ] User, Vendor, and Admin Role Implementation.

[ ] Database Schema Design for Menu (Will be Fixed).

Phase 3: Scaling & Analytics (Planned 📅)
[ ] Sales Analytics: Advanced data visualization for vendors using Recharts.

[ ] Payment Gateway Integration: Secure transactions with Midtrans/Stripe.

[ ] Mobile Optimization: Refining the UI for a flawless mobile-first experience.

🏗️ Architecture Highlights
Multi-Tenancy: Designed to isolate data between different vendors while maintaining a single shared database for scalability.

Security: Implemented granular access control, ensuring vendors can only access their specific dashboard and data.

Performance: Utilizing Next.js Server Components and Spring Boot's efficient processing for fast load times.
