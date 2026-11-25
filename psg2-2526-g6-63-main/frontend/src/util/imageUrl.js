const backendUrl = process.env.REACT_APP_BACKEND_URL || "http://localhost:8080";

export function resolveImageUrl(url) {
  if (!url) {
    return url;
  }
  if (url.startsWith("http://") || url.startsWith("https://")) {
    return url;
  }
  if (url.startsWith("/uploads/")) {
    return `${backendUrl}${url}`;
  }
  if (url.startsWith("uploads/")) {
    return `${backendUrl}/${url}`;
  }
  return url;
}
