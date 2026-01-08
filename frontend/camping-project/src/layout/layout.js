import { mountNavBar } from './nav.js'
import { mountFooter } from './footer.js'
import '../../public/styles/nav.css'
import '../../public/styles/footer.css'

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
