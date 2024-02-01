<script>
    import {createEventDispatcher} from "svelte";
    import Toasts from "./toast-notifications/Toasts.svelte";

    export let showModal;
    export let modalWidth = '';
    export let barrierDismissible = true;
    export function closeModal () {
        dialog.close();
    }

    let dialog; // HTMLDialogElement

    $: if (dialog && showModal) dialog.showModal();

    const dispatch = createEventDispatcher();

    const handlePrimaryClick = () => {
        dispatch('primary', {});
    }
</script>

<dialog bind:this={dialog} on:close={() => (showModal = false)} on:click|self={() => {if(barrierDismissible) dialog.close()}} class="container {modalWidth}">
    <Toasts />
    <div on:click|stopPropagation>
        <div class="p-4 font-extrabold">
            <slot name="header" />
        </div>
        <div class="p-4">
            <slot />
        </div>
        <div class="flex flex-col md:flex-row justify-center items-center sticky bottom-0 z-50 bg-[#374151] py-2.5">
            <button
                autofocus
                class="px-2 py-1 border-2 border-white rounded text-white md:hover:shadow-md md:hover:shadow-white"
                on:click={() => dialog.close()}>Close
            </button>
            <slot name="custom-buttons" />
        </div>
    </div>
</dialog>

<style>
    dialog {
        max-height: 50em;
        border-radius: 15px;
        border: none;
        padding: 0;
        position: absolute;
        top: 10%;
    }
    dialog::backdrop {
        backdrop-filter: blur(20px);
    }
    dialog[open] {
        animation: zoom 0.3s cubic-bezier(0.34, 1.56, 0.64, 1);
    }
    @keyframes zoom {
        from {
            transform: scale(0.95);
        }
        to {
            transform: scale(1);
        }
    }
    dialog[open]::backdrop {
        animation: fade 0.2s ease-out;
    }
    @keyframes fade {
        from {
            opacity: 0;
        }
        to {
            opacity: 1;
        }
    }
    button {
        display: block;
    }
</style>