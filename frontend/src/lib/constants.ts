export const FALLBACK_COVER = `data:image/svg+xml,${encodeURIComponent(`
  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 128 192">
    <defs>
      <linearGradient id="bookGradient" x1="0%" y1="0%" x2="100%" y2="0%">
        <stop offset="0%" style="stop-color:#d4c5a0;stop-opacity:1" />
        <stop offset="85%" style="stop-color:#f5f5dc;stop-opacity:1" />
        <stop offset="100%" style="stop-color:#e8dcc0;stop-opacity:1" />
      </linearGradient>
      <linearGradient id="spineGradient" x1="0%" y1="0%" x2="100%" y2="0%">
        <stop offset="0%" style="stop-color:#8b6f47;stop-opacity:1" />
        <stop offset="50%" style="stop-color:#a0826d;stop-opacity:1" />
        <stop offset="100%" style="stop-color:#8b6f47;stop-opacity:1" />
      </linearGradient>
    </defs>
    <rect width="128" height="192" fill="url(#bookGradient)"/>
    <rect x="0" y="0" width="12" height="192" fill="url(#spineGradient)"/>
    <rect x="16" y="16" width="96" height="160" fill="none" stroke="#8b4513" stroke-width="1.5" opacity="0.4"/>
    <rect x="20" y="20" width="88" height="152" fill="none" stroke="#8b4513" stroke-width="1" opacity="0.3"/>
    <path d="M 52 70 L 52 90 L 76 90 L 76 70 Z" fill="none" stroke="#8b4513" stroke-width="2" opacity="0.5"/>
    <path d="M 48 70 L 64 62 L 80 70" fill="none" stroke="#8b4513" stroke-width="2" opacity="0.5"/>
    <line x1="64" y1="70" x2="64" y2="90" stroke="#8b4513" stroke-width="1.5" opacity="0.5"/>
    <text x="64" y="115" font-family="serif" font-size="12" fill="#8b4513" text-anchor="middle" opacity="0.7">No Cover</text>
    <text x="64" y="130" font-family="serif" font-size="9" fill="#8b4513" text-anchor="middle" opacity="0.5">Available</text>
  </svg>
`)}`;
