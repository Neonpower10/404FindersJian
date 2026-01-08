/**
 * Mounts a footer into a given root element.
 *
 * @param {HTMLElement} root - The container element where the footer will be rendered.
 */
export function mountFooter(root) {
    root.innerHTML = `
    <footer class="footer">
      <div class="footer-content">
        <p>&copy; 2025 P&J CampingsSoftware</p>
        <p>
          Contact: <a href="mailto:info@pjsoftware.nl">info@pjsoftware.nl</a> |
          <a href="contactPage.html">Contactpagina</a>
        </p>
        <p>
          <a href="privacy.html">Privacybeleid</a> | 
          <a href="voorwaarden.html">Voorwaarden</a>
        </p>
      </div>
    </footer>
  `
}
