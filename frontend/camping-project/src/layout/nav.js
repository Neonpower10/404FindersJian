// Create the navigation bar
export function mountNavBar(root, options) {
    if (!options) {
        options = {};
    }
    
    // Create navbar HTML
    root.innerHTML = '<nav class="navbar">' +
        '<div class="nav-inner">' +
            '<div class="nav-left">' +
                '<button class="hamburger m3-icon-button" aria-label="Menu">' +
                    '<span class="hamburger-icon">' +
                        '<span class="hamburger-line"></span>' +
                        '<span class="hamburger-line"></span>' +
                        '<span class="hamburger-line"></span>' +
                    '</span>' +
                '</button>' +
                '<nav class="desktop-nav">' +
                    '<a href="/index.html" class="desktop-nav-item"><span class="material-symbols-outlined">home</span> Home</a>' +
                    '<a href="/bookingsPage.html" class="desktop-nav-item"><span class="material-symbols-outlined">calendar_today</span> Boek een plek!</a>' +
                    '<a href="/breadOrder.html" class="desktop-nav-item"><span class="material-symbols-outlined">bakery_dining</span> Broodjes bestellen</a>' +
                    '<a href="/facilitiesPage.html" class="desktop-nav-item"><span class="material-symbols-outlined">apps</span> Faciliteiten</a>' +
                    '<a href="/infoPage.html" class="desktop-nav-item"><span class="material-symbols-outlined">info</span>Over ons</a>' +
                '</nav>' +
            '</div>' +
            '<div class="nav-right">' +
                '<div class="profile-wrap">' +
                    '<button class="m3-icon-button btn-profile">' +
                        '<img src="/icons/profile.svg" alt="Profile" class="profile-icon">' +
                    '</button>' +
                    '<div class="menu m3-menu">' +
                        '<a href="/dashboardAdmin.html" class="m3-menu-item"><span class="material-symbols-outlined">dashboard</span> Dashboard</a>' +
                        '<a href="/adminBookings.html" class="m3-menu-item"><span class="material-symbols-outlined">event_note</span> Boekingbeheer</a>' +
                        '<a href="/breadManagement.html" class="m3-menu-item"><span class="material-symbols-outlined">bakery_dining</span> Broodbeheer</a>' +
                        '<a href="/moduleAdmin.html" class="m3-menu-item"><span class="material-symbols-outlined">extension</span> Modulebeheer</a>' +
                        '<a href="/designAdminPage.html" class="m3-menu-item"><span class="material-symbols-outlined">palette</span> Designbeheer</a>' +
                        '<a href="/facilitiesAdmin.html" class="m3-menu-item"><span class="material-symbols-outlined">apps</span> Faciliteitenbeheer</a>' +
                        '<a href="/adminHome.html" class="m3-menu-item"><span class="material-symbols-outlined">home</span> Homebeheer</a>' +
                        '<a href="/campingDataAdmin.html" class="m3-menu-item"><span class="material-symbols-outlined">data_object</span> Camping Data Beheer</a>' +
                        '<div class="m3-divider"></div>' +
                        '<a href="/accountLogin.html" data-login class="m3-menu-item"><span class="material-symbols-outlined">login</span> Login</a>' +
                        '<a href="/accountRegister.html" data-register class="m3-menu-item"><span class="material-symbols-outlined">person_add</span> Register</a>' +
                        '<div class="m3-divider"></div>' +
                        '<a href="/" data-logout class="m3-menu-item"><span class="material-symbols-outlined">logout</span> Logout</a>' +
                    '</div>' +
                '</div>' +
            '</div>' +
        '</div>' +
    '</nav>';
    
    // Create sidebar
    var overlay = document.createElement('div');
    overlay.className = 'sidebar-overlay';
    document.body.appendChild(overlay);
    
    var sidebar = document.createElement('div');
    sidebar.className = 'sidebar-menu m3-sidebar';
    sidebar.innerHTML = '<div class="sidebar-header">' +
        '<h2>Menu</h2>' +
        '<button class="sidebar-close m3-icon-button">×</button>' +
        '</div>' +
        '<nav class="sidebar-nav">' +
        '<a href="/index.html" class="m3-sidebar-item"><span class="material-symbols-outlined">home</span> Home</a>' +
        '<a href="/bookingsPage.html" class="m3-sidebar-item"><span class="material-symbols-outlined">calendar_today</span> Boek een plek!</a>' +
        '<a href="/breadOrder.html" class="m3-sidebar-item"><span class="material-symbols-outlined">bakery_dining</span> Broodjes bestellen</a>' +
        '<a href="/facilitiesPage.html" class="m3-sidebar-item"><span class="material-symbols-outlined">apps</span> Faciliteiten</a>' +
        '<a href="/infoPage.html" class="m3-sidebar-item"><span class="material-symbols-outlined">info</span> Camping info</a>' +
        '</nav>';
    document.body.appendChild(sidebar);
    
    // Profile menu
    var profileWrap = root.querySelector('.profile-wrap');
    var profileBtn = root.querySelector('.btn-profile');
    
    // When clicking the profile button, toggle the menu open/closed
    profileBtn.onclick = function(event) {
        // Stop the click event from bubbling up to the document
        // This prevents the outside click handler from immediately closing the menu
        event.stopPropagation();
        
        // Toggle the 'open' class: if it exists, remove it; if not, add it
        if (profileWrap.classList.contains('open')) {
            profileWrap.classList.remove('open');
        } else {
            profileWrap.classList.add('open');
        }
    };
    
    // Close the profile menu when clicking outside of it
    // This event listener is attached to the entire document (the whole page)
    document.addEventListener('click', function(event) {
        // Check if the profile menu is currently open
        if (profileWrap.classList.contains('open')) {
            // Check if the click happened outside the profile menu
            // contains() checks if the clicked element is inside profileWrap
            if (!profileWrap.contains(event.target)) {
                // If the click was outside, close the menu by removing the 'open' class
                profileWrap.classList.remove('open');
            }
        }
    });
    
    // Login/logout links
    var token = window.sessionStorage.getItem("jwt");
    var loginLink = root.querySelector('[data-login]');
    var registerLink = root.querySelector('[data-register]');
    var logoutLink = root.querySelector('[data-logout]');
    
    if (token) {
        if (loginLink) loginLink.style.display = "none";
        if (registerLink) registerLink.style.display = "none";
        if (logoutLink) logoutLink.style.display = "block";
    } else {
        if (loginLink) loginLink.style.display = "block";
        if (registerLink) registerLink.style.display = "block";
        if (logoutLink) logoutLink.style.display = "none";
    }
    
    if (logoutLink) {
        logoutLink.onclick = function(e) {
            e.preventDefault();
            window.sessionStorage.removeItem("jwt");
            window.sessionStorage.removeItem("role");
            profileWrap.classList.remove('open');
            window.location.href = "/accountLogin.html";
        };
    }

    const bookingLink = root.querySelector('a[href="/bookingsPage.html"]');

    if (bookingLink) {
        bookingLink.addEventListener("click", (e) => {
            const token = sessionStorage.getItem("jwt");
            if (!token) {
                e.preventDefault();
                sessionStorage.setItem("redirect_after_login", "/bookingsPage.html");
                window.location.href = "/accountLogin.html";
            }
        });
    }

    // Hamburger menu
    var hamburger = root.querySelector('.hamburger');
    
    hamburger.onclick = function() {
        if (sidebar.classList.contains('open')) {
            sidebar.classList.remove('open');
            overlay.classList.remove('open');
            hamburger.classList.remove('open');
        } else {
            sidebar.classList.add('open');
            overlay.classList.add('open');
            hamburger.classList.add('open');
        }
    };
    
    var closeBtn = sidebar.querySelector('.sidebar-close');
    closeBtn.onclick = function() {
        sidebar.classList.remove('open');
        overlay.classList.remove('open');
        hamburger.classList.remove('open');
    };
    
    overlay.onclick = function() {
        sidebar.classList.remove('open');
        overlay.classList.remove('open');
        hamburger.classList.remove('open');
    };
    
    var links = sidebar.querySelectorAll('a');
    for (var i = 0; i < links.length; i++) {
        links[i].onclick = function() {
            sidebar.classList.remove('open');
            overlay.classList.remove('open');
            hamburger.classList.remove('open');
        };
    }
}
