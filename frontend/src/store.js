import {writable} from 'svelte/store';

export const toasts = writable([]);

export const addToast = (toast) => {
    // Create a unique ID, so we can easily find/remove it.
    const id = Math.floor(Math.random() * 10000);
    // Setup some sensible defaults for a toast.
    const defaults = {
        id,
        type: "info",
        timeout: 3900,
    };
    // Push the toast to the top of the list of toasts.
    toasts.update((all) => [{...defaults, ...toast}, ...all]);
    // Dismiss toast after "timeout" amount of time.
    if (toast.timeout) {
        setTimeout(() => dismissToast(id), toast.timeout);
    } else {
        setTimeout(() => dismissToast(id), defaults.timeout);
    }
};

export const dismissToast = (id) => {
    toasts.update((all) => all.filter((t) => t.id !== id));
};