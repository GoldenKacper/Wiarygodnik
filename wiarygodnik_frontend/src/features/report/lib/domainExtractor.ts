export function getDomain(url: string): string {
    try {
        const parsed = new URL(url);
        return parsed.hostname;
    } catch {
        return url;
    }
}