export function getUrl(name){
    return new URL(`../static-resources/${name}`,import.meta.url).href
}