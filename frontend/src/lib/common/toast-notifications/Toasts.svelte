<script lang="ts">
    import { dismissToast, toasts } from '../../../store.js'
    import Toast from "./Toast.svelte";
</script>

{#if $toasts}
    <section id="toasts" class="flex flex-col mt-1 fixed w-screen justify-center top-0 left-0 right-0 z-10">
        {#each $toasts as toast (toast.id)}
            <Toast type={toast.type} on:dismiss={() => dismissToast(toast.id)}>{toast.message}</Toast>
        {/each}
    </section>
{/if}
<style>
    #toasts {
        z-index: 1000;
    }
</style>


