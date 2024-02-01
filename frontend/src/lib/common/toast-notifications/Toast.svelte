<script>
    import { createEventDispatcher } from "svelte";
    import { fade } from "svelte/transition";
    import CloseIcon from "../CloseIcon.svelte";

    const dispatch = createEventDispatcher();
    export let type = "error";
</script>

<article class="{type} flex justify-between w-screen rounded py-3 px-6 mt-0 mx-auto mb-2 md:w-auto" role="alert" transition:fade>
    <div>
        <slot />
    </div>

    <button class="close bg-transparent text-base text-neutral-700 border-0 p-0 my-0 mr-0 ml-6" on:click={() => dispatch("dismiss")}>
        <CloseIcon width="0.89em" />
    </button>
</article>

<style lang="postcss">
    .error {
        color: #721c24;
        background-color: #f8d7da;
        border-color: #f5c6cb;
    }
    .success {
        color: #155724;
        background-color: #d4edda;
        border-color: #c3e6cb;
    }
    .info {
        color: #0c5460;
        background-color: #d1ecf1;
        border-color: #bee5eb;
    }
</style>