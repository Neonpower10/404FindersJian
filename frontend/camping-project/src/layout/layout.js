import { mountNavBar } from './nav.js'
import { mountFooter } from './footer.js'
import '../../public/styles/nav.css'
import '../../public/styles/footer.css'

// Load Material Symbols (Material 3)
function loadMaterialIcons() {
    if (!document.querySelector('link[href*="Material+Symbols"]')) {
        const link = document.createElement('link');
        link.href = 'https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200';
        link.rel = 'stylesheet';
        document.head.appendChild(link);
    }
}

function ensureContainer(id, position = 'start') {
    let el = document.getElementById(id)
    if (!el) {
        el = document.createElement('div')
        el.id = id
        if (position === 'start') {
            document.body.prepend(el)
        } else {
            document.body.appendChild(el)
        }
    }
    return el
}

function initLayout() {
    loadMaterialIcons();
    const navRoot = ensureContainer('nav-root', 'start')
    const footerRoot = ensureContainer('footer-root', 'end')

    mountNavBar(navRoot)
    mountFooter(footerRoot)
}

if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', initLayout)
} else {
    initLayout()
}
