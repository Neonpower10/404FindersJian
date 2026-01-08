/**
 * Mounts a theme-aware navigation bar into a given root element.
 *
 * @param {HTMLElement} root - The container element where the navbar will be rendered.
 * @param {Object} [options={}] - Optional settings for the navbar.
 * @param {string} [options.title='Camping Platform'] - The title of the platform (currently unused).
 * @param {string} [options.logo=''] - The logo for the platform (currently unused).
 *
 * Features:
 * - Profile menu can be opened/closed by clicking the profile button.
 * - Menu closes when pressing the Escape key.
 * - Logout button placeholder.
 * - Colors and background adapt to the currently loaded theme (via CSS variables).
 */
export function mountNavBar(root, { title = 'Camping Platform', logo = '' } = {}) {
    root.innerHTML = `
    <nav class="navbar" style="background: var(--navbar-bg); color: var(--text);">
      <div class="nav-inner">
        <div class="nav-left">
          <button class="hamburger" aria-label="Menu" style="color: var(--text)">☰</button>
        </div>

        <div class="nav-right">
          <button class="icon-btn btn-bell" aria-label="Notifications">
            <img src="/icons/notifications.svg" alt="Notifications">
          </button>

          <div class="profile-wrap">
            <button class="icon-btn btn-profile" aria-expanded="false" aria-haspopup="menu" style="color: var(--text)">
              <img src="/icons/profile.svg" alt="Profile">
            </button>
            <div class="menu" role="menu" aria-label="Profile menu" style="background: var(--card-bg); color: var(--text)">
              <a role="menuitem" href="/index.html">Home</a>
              <a role="menuitem" href="/dashboardAdmin.html" class="admin-visibility">Dashboard</a>
              <a role="menuitem" href="/adminBookings.html" class="admin-visibility">Boekingbeheer</a>
              <a role="menuitem" href="/moduleAdmin.html" class="admin-visibility">Modulebeheer</a>
              <a role="menuitem" href="/breadManagement.html" class="admin-visibility">Broodbeheer</a>
              <a role="menuitem" href="/designAdminPage.html" class="admin-visibility">Designbeheer</a>
              <a role="menuitem" href="/facilitiesAdmin.html" class="admin-visibility">Faciliteitenbeheer</a>
              <a role="menuitem" href="/bookingsPage.html">Boek een plek!</a>
              <a role="menuitem" href="/facilitiesPage.html">Faciliteiten</a>
              <a role="menuitem" href="/breadOrder.html">Broodje bestellen?</a>
              <a role="menuitem" href="/accountLogin.html" data-login>Login</a>
              <a role="menuitem" href="/accountRegister.html" data-register>Register</a>
              <hr>
              <a role="menuitem" href="/" data-logout>Logout</a>
            </div>
          </div>
         </div>
      </div>
    </nav>
    `;
    /** Menu handling */
    const wrap = root.querySelector('.profile-wrap');
    const btn = root.querySelector('.btn-profile');
    const menu = root.querySelector('.menu');
    function openMenu() {
        wrap.classList.add('open');
        btn.setAttribute('aria-expanded', 'true');
    }
    function closeMenu() {
        wrap.classList.remove('open');
        btn.setAttribute('aria-expanded', 'false');
    }
    btn.addEventListener('click', (e) => {
        e.stopPropagation();
        const isOpen = wrap.classList.contains('open');
        isOpen ? closeMenu() : openMenu();
    });
    // Close menu on Escape key
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape') closeMenu();
    });
    // Login/Register show/hide based on JWT
    const token = window.sessionStorage.getItem("jwt");
    const loginLink = root.querySelector('[data-login]');
    const registerLink = root.querySelector('[data-register]');
    const logoutLink = root.querySelector('[data-logout]');
    if (token) {
        // User is logged in -> hide Login & Register, show Logout
        if (loginLink) loginLink.style.display = "none";
        if (registerLink) registerLink.style.display = "none";
        if (logoutLink) logoutLink.style.display = "block";
    } else {
        // Not logged in -> show Login & Register, hide Logout
        if (loginLink) loginLink.style.display = "block";
        if (registerLink) registerLink.style.display = "block";
        if (logoutLink) logoutLink.style.display = "none";
    }
    // Logout: delete jwt + go to login
    if (logoutLink) {
        logoutLink.addEventListener("click", (e) => {
            e.preventDefault();
            window.sessionStorage.removeItem("jwt");
            window.sessionStorage.removeItem("role");
            closeMenu();
            window.location.href = "/accountLogin.html";
        });
    }

    /** Optional: update colors dynamically if theme changes at runtime */
    const observer = new MutationObserver(() => {
        root.querySelectorAll('nav, .menu, .btn-profile, .hamburger').forEach(el => {
            el.style.background = getComputedStyle(document.documentElement).getPropertyValue('--card-bg');
            el.style.color = getComputedStyle(document.documentElement).getPropertyValue('--text');
        });
    });
    observer.observe(document.documentElement, { attributes: true, attributeFilter: ['style'] });

    // ➜ Admin-links verbergen voor niet-admins
    const role = window.sessionStorage.getItem("role");
    const isAdmin = token && role === "admin";

    root.querySelectorAll(".admin-visibility").forEach(el => {
        el.style.display = isAdmin ? "block" : "none";
    });
}