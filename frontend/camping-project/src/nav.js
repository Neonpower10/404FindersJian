/**
 * Mounts a navigation bar into a given root element.
 *
 * @param {HTMLElement} root - The container element where the navbar will be rendered.
 * @param {Object} [options={}] - Optional settings for the navbar.
 * @param {string} [options.title='Camping Platform'] - The title of the platform (currently unused).
 * @param {string} [options.logo=''] - The logo for the platform (currently unused).
 *
 * The navbar includes:
 * - A hamburger button (menu placeholder).
 * - A notifications button.
 * - A profile button with dropdown menu (Dashboard, Bookings, Notifications, Settings, Logout).
 *
 * Features:
 * - Profile menu can be opened/closed by clicking the profile button.
 * - Menu closes when pressing the Escape key.
 * - Logout button currently redirects to the homepage.
 */
export function mountNavBar(root, {title = 'Camping Platform', logo = ''} = {}) {
    root.innerHTML = `
    <nav class="navbar">
      <div class="nav-inner">
        <div class="nav-left">
          <button class="hamburger" aria-label="Menu">☰</button>
        </div>

        <div class="nav-right">
          <button class="icon-btn btn-bell" aria-label="Meldingen">
            <img src="/icons/notifications.svg" alt="Meldingen">
          </button>

          <div class="profile-wrap">
            <button class="icon-btn btn-profile" aria-expanded="false" aria-haspopup="menu">
            <img src="/icons/profile.svg" alt="Profiel">
            </button>
            <div class="menu" role="menu" aria-label="Profielmenu">
              <a role="menuitem" href="/dashboard.html">Dashboard</a>
              <a role="menuitem" href="/bookingsPage.html">Boekingen</a>
              <a role="menuitem" href="/alerts.html">Meldingen</a>
              <a role="menuitem" href="/settings.html">Instellingen</a>
              <a role="menuitem" href="/breadManagement.html">Broodjesbeheer</a>
              <a role="menuitem" href="/designAdminPage.html">Styling</a>
              <a role="menuitem" href="/facilitiesAdmin.html">Faciliteiten</a>
              <a role="menuitem" href="/login.html">Inloggen</a>
              <a role="menuitem" href="/register.html">Registreren</a>
              <hr>
              <a role="menuitem" href="/" data-logout>Log uit</a>
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

    // Close on Escape
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape') closeMenu();
    });

    // Logout placeholder
    const logout = root.querySelector('[data-logout]');
    if (logout) {
        logout.addEventListener('click', (e) => {
            closeMenu();
        });
    }
}
